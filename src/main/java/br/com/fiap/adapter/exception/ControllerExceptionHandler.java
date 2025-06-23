package br.com.fiap.adapter.exception;

import br.com.fiap.core.exception.ProdutoComSkuJaCadastradoException;
import br.com.fiap.core.exception.ProdutoNaoEncontradoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(
            jakarta.validation.ConstraintViolationException ex, WebRequest request) {

        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        List<Problem.Field> problemFields = ex.getConstraintViolations().stream()
                .map(violation -> Problem.Field.builder()
                        .name(violation.getPropertyPath().toString())
                        .userMessage(violation.getMessage())
                        .build())
                .toList();

        Problem problem = createProblemBuilder(HttpStatus.BAD_REQUEST, problemType, detail)
                .userMessage(detail)
                .fields(problemFields)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        BindingResult bindingResult = ex.getBindingResult();
        List<Problem.Field> problemFilds = bindingResult
                .getFieldErrors()
                .stream()
                .map(problemFild -> Problem.Field.builder()
                        .name(problemFild.getField())
                        .userMessage(problemFild.getDefaultMessage())
                        .build()).toList();


        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(detail)
                .fields(problemFilds)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<Object> handleEstadoNaoEncontradoException(ProdutoNaoEncontradoException e, WebRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;

        ProblemType problemaType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = e.getMessage();
        Problem problema = createProblemBuilder(status,problemaType, detail)
                .userMessage(detail)
                .build();
        return handleExceptionInternal(e, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ProdutoComSkuJaCadastradoException.class)
    public ResponseEntity<Object> handleProdutoComSkuJaCadastradoException(ProdutoComSkuJaCadastradoException e, WebRequest request){
        HttpStatus status = HttpStatus.CONFLICT;

        ProblemType problemaType = ProblemType.SKU_JACADASTRADO;
        String detail = e.getMessage();
        Problem problema = createProblemBuilder(status,problemaType, detail)
                .userMessage(detail)
                .build();
        return handleExceptionInternal(e,problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.DADOS_INVALIDOS;

        String detail;
        String mensagemUsuario;

        if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains("cpf")) {
            detail = "Já existe um cliente cadastrado com o CPF informado.";
            mensagemUsuario = "CPF já cadastrado no sistema. Por favor, verifique o CPF informado.";
        } else {
            detail = "Ocorreu uma violação de integridade dos dados.";
            mensagemUsuario = "Não foi possível processar a requisição. Por favor, verifique os dados informados.";
        }

        Problem problema = createProblemBuilder(status, problemType, detail)
                .userMessage(mensagemUsuario)
                .build();

        return handleExceptionInternal(e, problema, new HttpHeaders(), status, request);
    }


    private Problem.ProblemBuilder createProblemBuilder(HttpStatusCode status, ProblemType problemaType, String detail){

        return Problem.builder()
                .status(status.value())
                .type(problemaType.getUri())
                .title(problemaType.getTitle())
                .timestamp(LocalDateTime.now())
                .detail(detail);
    }

    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType problemaType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.",ex.getRequestURL());

        Problem problem = createProblemBuilder(status, problemaType, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

}