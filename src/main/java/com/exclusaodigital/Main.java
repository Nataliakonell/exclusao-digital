package com.exclusaodigital;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.exclusaodigital.Model.Categoria;
import com.exclusaodigital.Model.MotivoExclusao;
import com.exclusaodigital.Model.Subcategoria;
import com.exclusaodigital.Repository.CategoriaRepository;
import com.exclusaodigital.Repository.MotivoExclusaoRepository;
import com.exclusaodigital.Repository.SubcategoriaRepository;

public class Main {

    private static void exibirMotivos(List<MotivoExclusao> motivos) {
        for (MotivoExclusao motivo : motivos) {
            System.out.println("Detalhes dos Motivos de Exclusão:");
            System.out.printf("  Falta de Interesse/Necessidade: %d%n", motivo.getFaltaInteresse());
            System.out.printf("  Não Saber Usar: %d%n", motivo.getNaoSaberUsar());
            System.out.printf("  Não Ter Onde Acessar: %d%n", motivo.getNaoTerOndeAcessar());
            System.out.printf("  Ser Muito Caro: %d%n", motivo.getSerMuitoCaro());
            System.out.printf("  Segurança/Privacidade: %d%n", motivo.getSegurancaPrivacidade());
            System.out.printf("  Evitar Conteúdo Perigoso: %d%n", motivo.getEvitarConteudoPerigoso());
            System.out.printf("  Outro Motivo: %d%n", motivo.getOutroMotivo());
            System.out.println("---------------------------");
        }
    }

    private static void pausar(Scanner scanner) {
        System.out.println("\nPressione ENTER para voltar ao menu...");
        scanner.nextLine();
    }

    private static void exibirMenu() {
        System.out.println("\n=== Menu Exclusão Digital ===");
        System.out.println("1. Listar todas as categorias");
        System.out.println("2. Consultar subcategorias e motivos por categoria");
        System.out.println("3. Listar todas as subcategorias");
        System.out.println("4. Menu de consultas avançadas");
        System.out.println("5. Listar todos os motivos");
        System.out.println("6. Ver totais acumulados por categoria/subcategoria");
        System.out.println("7. Filtrar por Categoria/Subcategoria/Motivo");
        System.out.println("0. Sair");
        System.out.print("Escolha: ");
    }

    private static void exibirMenuTipoMotivo() {
        System.out.println("\nSelecione o tipo de motivo para filtrar:");
        System.out.println("1. Falta de interesse/necessidade");
        System.out.println("2. Não saber usar");
        System.out.println("3. Não ter onde acessar");
        System.out.println("4. Ser muito caro");
        System.out.println("5. Preocupações com segurança/privacidade");
        System.out.println("6. Evitar conteúdo perigoso");
        System.out.println("7. Outro motivo");
        System.out.print("Escolha: ");
    }

    private static void exibirListaCategorias(List<Categoria> categorias) {
        if (categorias.isEmpty()) {
            System.out.println("\nNenhuma categoria cadastrada no sistema.");
        } else {
            System.out.println("\nLista de todas as categorias:");
            for (Categoria cat : categorias) {
                System.out.println(cat.getId() + " - " + cat.getNome());
            }
        }
    }

    private static void exibirSubcategoriasEMotivos(List<Subcategoria> subs, MotivoExclusaoRepository motivoRepo) {
        if (subs.isEmpty()) {
            System.out.println("Nenhuma subcategoria encontrada para esta categoria.");
        } else {
            for (Subcategoria sub : subs) {
                System.out.printf("%d - %s%n", sub.getId(), sub.getNome());
                List<MotivoExclusao> motivos = motivoRepo.buscarPorSubcategoria(sub);
                if (motivos.isEmpty()) {
                    System.out.println("Nenhum motivo de exclusão registrado para esta subcategoria.");
                } else {
                    exibirMotivos(motivos);
                }
            }
        }
    }

    private static void exibirTodasSubcategoriasAgrupadas(List<Subcategoria> todasSubs) {
        if (todasSubs.isEmpty()) {
            System.out.println("\nNenhuma subcategoria cadastrada no sistema.");
        } else {
            System.out.println("\nLista de todas as subcategorias:");
            String catAtual = "";
            for (Subcategoria sub : todasSubs) {
                if (!sub.getCategoria().getNome().equals(catAtual)) {
                    catAtual = sub.getCategoria().getNome();
                    System.out.println("\nCategoria: " + catAtual);
                }
                System.out.println("  - " + sub.getNome());
            }
        }
    }

    private static void exibirResultadosMotivosFiltrados(List<MotivoExclusao> motivosFiltrados) {
        if (motivosFiltrados.isEmpty()) {
            System.out.println("Nenhum resultado encontrado com os critérios especificados.");
        } else {
            for (MotivoExclusao motivo : motivosFiltrados) {
                System.out.println("\nCategoria: " + motivo.getSubcategoria().getCategoria().getNome());
                System.out.println("Subcategoria: " + motivo.getSubcategoria().getNome());
                exibirMotivos(List.of(motivo));
            }
        }
    }

    private static void exibirTodosMotivosAgrupados(List<MotivoExclusao> todosMotivos) {
        if (todosMotivos.isEmpty()) {
            System.out.println("\nNenhum motivo de exclusão digital cadastrado no sistema.");
        } else {
            System.out.println("\nLista de todos os motivos de exclusão digital:");
            String categoriaAtualMotivos = "";
            String subcategoriaAtual = "";
            for (MotivoExclusao motivo : todosMotivos) {
                String catNome = motivo.getSubcategoria().getCategoria().getNome();
                String subNome = motivo.getSubcategoria().getNome();

                if (!catNome.equals(categoriaAtualMotivos)) {
                    categoriaAtualMotivos = catNome;
                    System.out.println("\nCategoria: " + categoriaAtualMotivos);
                }
                if (!subNome.equals(subcategoriaAtual)) {
                    subcategoriaAtual = subNome;
                    System.out.println("\n  Subcategoria: " + subcategoriaAtual);
                }
                exibirMotivos(List.of(motivo));
            }
        }
    }

    private static void exibirTotaisAcumulados(Map<String, Map<String, Integer>> totaisAcumulados) {
        if (totaisAcumulados.isEmpty()) {
            System.out.println("\nNenhum dado de exclusão digital encontrado para gerar totais acumulados.");
        } else {
            System.out.println("\nTotais Acumulados por Categoria e Subcategoria:");
            System.out.println("===========================================");

            String categoriaAtualAcumulado = "";
            for (Map.Entry<String, Map<String, Integer>> entry : totaisAcumulados.entrySet()) {
                String[] partes = entry.getKey().split(" - ");
                String categoria = partes[0];
                String subcategoria = partes[1];

                if (!categoria.equals(categoriaAtualAcumulado)) {
                    categoriaAtualAcumulado = categoria;
                    System.out.println("\nCategoria: " + categoria);
                }

                System.out.println("\n  Subcategoria: " + subcategoria);
                Map<String, Integer> motivos = entry.getValue();

                int totalSubcategoria = motivos.values().stream().mapToInt(Integer::intValue).sum();

                for (Map.Entry<String, Integer> motivo : motivos.entrySet()) {
                    double porcentagem = totalSubcategoria > 0
                            ? (motivo.getValue() * 100.0 / totalSubcategoria)
                            : 0;
                    System.out.printf("    %s: %d (%.1f%%)\n",
                            motivo.getKey(), motivo.getValue(), porcentagem);
                }
                System.out.println("    Total na subcategoria: " + totalSubcategoria);
                System.out.println("    ------------------------");
            }
        }
    }

    private static void listarCategoriasDisponiveis(List<Categoria> categoriasDisponiveis) {
        System.out.println("\nCategorias disponíveis:");
        for (Categoria c : categoriasDisponiveis) {
            System.out.println(c.getId() + " - " + c.getNome());
        }
    }

    private static void listarSubcategoriasDisponiveis(List<Subcategoria> subcategoriasDisponiveis, String nomeCategoria) {
        if (subcategoriasDisponiveis.isEmpty()) {
            System.out.println("\nNão há subcategorias disponíveis para " + nomeCategoria);
        } else {
            System.out.println("\nSubcategorias disponíveis para " + nomeCategoria + ":");
            for (Subcategoria s : subcategoriasDisponiveis) {
                System.out.println(s.getId() + " - " + s.getNome());
            }
        }
    }

    private static void exibirResultadosFiltroAvancado(List<MotivoExclusao> resultadosFiltrados) {
        if (resultadosFiltrados.isEmpty()) {
            System.out.println("\nNenhum resultado encontrado com os critérios especificados.");
        } else {
            System.out.println("\nResultados encontrados:");
            System.out.println("======================");
            String categoriaAtual = "";
            String subcategoriaAtual = "";

            for (MotivoExclusao motivo : resultadosFiltrados) {
                Categoria categoria = motivo.getSubcategoria().getCategoria();
                Subcategoria subcategoria = motivo.getSubcategoria();

                if (!categoria.getNome().equals(categoriaAtual)) {
                    categoriaAtual = categoria.getNome();
                    System.out.println("\nCategoria: [ID: " + categoria.getId() + "] " + categoria.getNome());
                }
                if (!subcategoria.getNome().equals(subcategoriaAtual)) {
                    subcategoriaAtual = subcategoria.getNome();
                    System.out.println("Subcategoria: [ID: " + subcategoria.getId() + "] " + subcategoria.getNome());
                }

                System.out.println("Motivo ID: " + motivo.getId());
                exibirMotivos(List.of(motivo));
            }
        }
    }

    public static void main(String[] args) {
        CategoriaRepository categoriaRepo = new CategoriaRepository();
        SubcategoriaRepository subcategoriaRepo = new SubcategoriaRepository();
        MotivoExclusaoRepository motivoRepo = new MotivoExclusaoRepository();

        int opcao = -1;
        try (Scanner sc = new Scanner(System.in)) {

            while (opcao != 0) {
                exibirMenu();

                try {
                    opcao = sc.nextInt();
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Erro: Por favor, digite apenas números.");
                    sc.nextLine();
                    opcao = -1;
                    pausar(sc);
                    continue;
                }
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        List<Categoria> categorias = categoriaRepo.buscarTodos();
                        exibirListaCategorias(categorias);
                        pausar(sc);
                        break;

                    case 2:
                        System.out.print("Informe o ID da categoria: ");
                        int catId = sc.nextInt();
                        sc.nextLine();
                        Categoria cat = categoriaRepo.buscarPorId(catId);

                        if (cat == null) {
                            System.out.println("Categoria não encontrada!");
                        } else {
                            List<Subcategoria> subs = subcategoriaRepo.buscarPorCategoria(cat);
                            exibirSubcategoriasEMotivos(subs, motivoRepo);
                        }
                        pausar(sc);
                        break;

                    case 3:
                        List<Subcategoria> todasSubs = subcategoriaRepo.buscarTodasSubcategorias();
                        exibirTodasSubcategoriasAgrupadas(todasSubs);
                        pausar(sc);
                        break;

                    case 4:
                        exibirMenuTipoMotivo();
                        int motivoOpcao = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Digite o valor mínimo de ocorrências: ");
                        int valorMinimo = sc.nextInt();
                        sc.nextLine();

                        String campoMotivo = switch (motivoOpcao) {
                            case 1 -> "falta_interesse_necessidade";
                            case 2 -> "nao_saber_usar";
                            case 3 -> "nao_ter_onde_acessar";
                            case 4 -> "ser_muito_caro";
                            case 5 -> "preocupacoes_seguranca_privacidade";
                            case 6 -> "evitar_contato_conteudo_perigoso";
                            case 7 -> "outro_motivo";
                            default -> {
                                System.out.println("Opção inválida!");
                                yield "";
                            }
                        };

                        if (!campoMotivo.isEmpty()) {
                            List<MotivoExclusao> motivosFiltrados = motivoRepo.buscarPorMotivoPrevalente(campoMotivo, valorMinimo);
                            exibirResultadosMotivosFiltrados(motivosFiltrados);
                        }
                        pausar(sc);
                        break;

                    case 5:
                        List<MotivoExclusao> todosMotivos = motivoRepo.buscarTodosMotivos();
                        exibirTodosMotivosAgrupados(todosMotivos);
                        pausar(sc);
                        break;

                    case 6:
                        Map<String, Map<String, Integer>> totaisAcumulados = motivoRepo.buscarTotaisAcumulados();
                        exibirTotaisAcumulados(totaisAcumulados);
                        pausar(sc);
                        break;

                    case 7:
                        System.out.println("\n=== Filtro por Categoria/Subcategoria/Motivo ===");
                        List<Categoria> categoriasDisponiveis = categoriaRepo.buscarTodos();
                        listarCategoriasDisponiveis(categoriasDisponiveis);

                        System.out.print("\nDigite o ID da categoria (0 para qualquer categoria): ");
                        int categoriaId = sc.nextInt();
                        sc.nextLine();

                        if (categoriaId > 0) {
                            Categoria catSelecionada = categoriaRepo.buscarPorId(categoriaId);
                            if (catSelecionada != null) {
                                List<Subcategoria> subcategoriasDisponiveis = subcategoriaRepo.buscarPorCategoria(catSelecionada);
                                listarSubcategoriasDisponiveis(subcategoriasDisponiveis, catSelecionada.getNome());
                            } else {
                                System.out.println("\nCategoria não encontrada!");
                                pausar(sc);
                                continue;
                            }
                        }

                        System.out.print("\nDigite o ID da subcategoria (0 para qualquer subcategoria): ");
                        int subcategoriaId = sc.nextInt();
                        sc.nextLine();

                        exibirMenuTipoMotivo();
                        int tipoMotivo = sc.nextInt();
                        sc.nextLine();

                        String campoMotivoFiltro = switch (tipoMotivo) {
                            case 1 -> "falta_interesse_necessidade";
                            case 2 -> "nao_saber_usar";
                            case 3 -> "nao_ter_onde_acessar";
                            case 4 -> "ser_muito_caro";
                            case 5 -> "preocupacoes_seguranca_privacidade";
                            case 6 -> "evitar_contato_conteudo_perigoso";
                            case 7 -> "outro_motivo";
                            default -> {
                                System.out.println("Opção inválida!");
                                yield "";
                            }
                        };

                        if (!campoMotivoFiltro.isEmpty()) {
                            List<MotivoExclusao> resultadosFiltrados = motivoRepo.buscarPorIds(
                                    categoriaId, subcategoriaId, campoMotivoFiltro);
                            exibirResultadosFiltroAvancado(resultadosFiltrados);
                        }
                        pausar(sc);
                        break;

                    case 0:
                        System.out.println("Saindo...");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                        pausar(sc);
                        break;
                }
            }
        }
    }
}