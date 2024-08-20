package com.example.movie.review.controller;

import com.example.movie.review.model.Filme;
import com.example.movie.review.model.Analise;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author rebeca
 */
@Controller
@RequestMapping("/filmes")
@Tag(name = "Filmes", description = "Gerenciamento de filmes")
public class FilmeController {

    private List<Filme> filmes = new ArrayList<>();
    private AtomicLong filmeIdCounter = new AtomicLong();

    @Operation(summary = "Listar todos os filmes")
    @GetMapping
    public String listarFilmes(Model model) {
        model.addAttribute("filmes", filmes);
        return "listarFilmes";
    }

    @Operation(summary = "Mostrar formulário de cadastro de filme")
    @GetMapping("/cadastro")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("filme", new Filme());
        return "cadastroFilme";
    }

    @Operation(summary = "Cadastrar um novo filme")
    @PostMapping("/cadastro")
    public String cadastrarFilme(@ModelAttribute Filme filme) {
        filme.setId(filmeIdCounter.incrementAndGet());
        filmes.add(filme);
        return "redirect:/filmes";
    }

    @Operation(summary = "Detalhes do filme")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalhes do filme"),
        @ApiResponse(responseCode = "404", description = "Filme não encontrado")
    })
    @GetMapping("/{id}")
    public String detalhesFilme(@PathVariable Long id, Model model) {
        Filme filme = filmes.stream().filter(f -> f.getId().equals(id)).findFirst().orElse(null);
        model.addAttribute("filme", filme);
        model.addAttribute("analise", new Analise());
        return "detalhesFilme";
    }

    @Operation(summary = "Adicionar análise ao filme")
    @PostMapping("/{id}/analise")
    public String adicionarAnalise(@PathVariable Long id, @ModelAttribute Analise analise) {
        Filme filme = filmes.stream().filter(f -> f.getId().equals(id)).findFirst().orElse(null);
        if (filme != null) {
            analise.setFilme(filme);
        }

        return "redirect:/filmes/" + id;
    }

}
