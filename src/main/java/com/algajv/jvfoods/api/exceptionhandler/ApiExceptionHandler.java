package com.algajv.jvfoods.api.exceptionhandler;

import com.algajv.jvfoods.core.validation.ValidacaoException;
import com.algajv.jvfoods.domain.exception.EntidadeEmUsoException;
import com.algajv.jvfoods.domain.exception.EntidadeNaoEncontradaException;
import com.algajv.jvfoods.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICO_USUARIO_FINAL = "Ocorreu um erro interno inesperado. Tente novamente. Se o problema persistir, contate o administrador do sistema.";
    public static final String MSG_DADOS_INVALIDOS = "Um ou mais campos estão inválidos.";

    @Autowired
    private MessageSource messageSource;

    // EXCEPTION MAIS ESPECÍFICA
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        Problem problem = createProblem(status, problemType, e.getMessage());
        problem.setUserMessage(e.getMessage());  // sendo um problema mais específico, pode-se setar a mesma informação do 'detail' no 'userMessage'.
        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    // EXCEPTION MAIS GERAL
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request) {

            HttpStatus status = HttpStatus.BAD_REQUEST;
            ProblemType problemType = ProblemType.ERRO_DE_NEGOCIO;
            Problem problem = createProblem(status, problemType, e.getMessage());
            problem.setUserMessage(e.getMessage());
            return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }


    // EXCEPTION MAIS ESPECÍFICA
    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request){

        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
        Problem problem = createProblem(status, problemType, e.getMessage());
        problem.setUserMessage(e.getMessage());
        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<Object> handleValidacaoException(ValidacaoException ex, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }




    private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
         String detail = MSG_DADOS_INVALIDOS;

        List<Problem.ObjectValidated> problemObjects = bindingResult.getAllErrors().stream().map(objectError -> {
            String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
            String name = objectError.getObjectName();
            if(objectError instanceof FieldError) {
                name = ((FieldError) objectError).getField();
            }

            return Problem.ObjectValidated.builder()
                    .name(name)
                    .userMessage(message)
                    .build();

        }).collect(Collectors.toList());


        Problem problem = createProblem(status, problemType, detail);
        problem.setUserMessage(detail);
        problem.setObjects(problemObjects);

        return handleExceptionInternal(ex, problem, headers, status, request);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaughtException(Exception e, WebRequest request){


        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
        String detail = MSG_ERRO_GENERICO_USUARIO_FINAL;
        e.printStackTrace();
        Problem problem = createProblem(status, problemType, detail);
        problem.setUserMessage(detail);  // sendo um problema mais interno e para o qual o usuário não pode fazer muita coisa, 'uerMessage' recebe uma mensagem mais genérica.
        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }



    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if(rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException)rootCause, headers, status, request);
        } else if(rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException)rootCause, headers, status, request);
        }

        ProblemType problemType = ProblemType.CORPO_INVALIDO;
        Problem problem = createProblem(status, problemType, ex.getMessage());
        String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
        problem.setUserMessage(MSG_ERRO_GENERICO_USUARIO_FINAL);
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if(ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatchException( (MethodArgumentTypeMismatchException)ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.PARAMETRO_URL_INVALIDO;
        String detail = String.format("O parâmetro  da URL '%s' recebeu um valor de tipo inválido. Corrija com valor de tipo compatível.", ex.getParameter().getParameterName());
        Problem problem = createProblem(status, problemType, detail);
        problem.setUserMessage(MSG_ERRO_GENERICO_USUARIO_FINAL);
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = String.format("O recurso '%s', que você tentou acessar, não existe.", ex.getRequestURL());
        Problem problem = createProblem(status, problemType, detail);
        problem.setUserMessage(MSG_ERRO_GENERICO_USUARIO_FINAL);
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        //concatenando os campos com '.'
        String path = ex.getPath().stream().map(reference -> reference.getFieldName()).collect(Collectors.joining("."));

        ProblemType problemType = ProblemType.CORPO_INVALIDO;
        String detail = String.format("A propriedade '%s' recebeu um valor de tipo incompatível ('%s'). Corrija para um valor do tipo %s.",
                                        path, ex.getValue(), ex.getTargetType().getSimpleName() );
        Problem problem = createProblem(status, problemType, ex.getMessage());
        problem.setUserMessage(MSG_ERRO_GENERICO_USUARIO_FINAL);
        return handleExceptionInternal(ex, problem, headers, status, request);

    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {


        //concatenando os campos com '.'
        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.CORPO_INVALIDO;
        String detail = String.format("A propriedade '%s' não existe. Corrija ou remova essa propriedade.",
                                        path);
        Problem problem = createProblem(status, problemType, detail);
        problem.setUserMessage(MSG_ERRO_GENERICO_USUARIO_FINAL);
        return handleExceptionInternal(ex, problem, headers, status, request);

    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(),headers, status,request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if(body == null) {
            body = new Problem(status.value(), null, status.getReasonPhrase(),null);
        } else if (body instanceof String) {
            body = new Problem(status.value(), null, (String) body,null);
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem createProblem(HttpStatus status, ProblemType problemType, String detail) {
        Problem problem = new Problem(status.value(), problemType.getUri(), problemType.getTitle(), detail);
        problem.setTimestamp(OffsetDateTime.now());
        return problem;
    }

    private String joinPath(List<Reference> referenceList) {
        return referenceList.stream().map(reference -> reference.getFieldName()).collect(Collectors.joining("."));
    }
}
