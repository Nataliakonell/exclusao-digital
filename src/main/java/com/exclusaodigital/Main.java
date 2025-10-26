package com.exclusaodigital;

import java.util.List;
import java.util.Scanner;

import com.exclusaodigital.Model.Categoria;
import com.exclusaodigital.Model.MotivoExclusao;
import com.exclusaodigital.Model.Subcategoria;
import com.exclusaodigital.Repository.CategoriaRepository;
import com.exclusaodigital.Repository.MotivoExclusaoRepository;
import com.exclusaodigital.Repository.SubcategoriaRepository;

public class Main {

    public static void main(String[] args) {
        CategoriaRepository categoriaRepo = new CategoriaRepository();
        SubcategoriaRepository subcategoriaRepo = new SubcategoriaRepository();
        MotivoExclusaoRepository motivoRepo = new MotivoExclusaoRepository();

        Scanner sc = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 3) {
            System.out.println("\n--- Menu Exclusão Digital ---");
            System.out.println("1. Listar categorias");
            System.out.println("2. Consultar subcategorias e motivos");
            System.out.println("3. Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            if (opcao == 1) {
                List<Categoria> categorias = categoriaRepo.buscarTodos();
                for (Categoria cat : categorias) {
                    System.out.println(cat.getNome()); // imprime id e nome
                }
            } else if (opcao == 2) {
                System.out.print("Informe o ID da categoria: ");
                int catId = sc.nextInt();
                sc.nextLine();
                Categoria cat = categoriaRepo.buscarPorId(catId);
                if (cat == null) {
                    System.out.println("Categoria não encontrada!");
                    continue;
                }

                List<Subcategoria> subs = subcategoriaRepo.buscarPorCategoria(cat);

                for (Subcategoria sub : subs) {
                    System.out.println("\n=== Subcategoria: " + sub.getNome() + " ===");

                    List<MotivoExclusao> motivos = motivoRepo.buscarPorSubcategoria(sub);

                    for (MotivoExclusao motivo : motivos) {
                        System.out.println("Motivos de Exclusão:");
                        System.out.println("Falta de Interesse/Necessidade: " + motivo.getFaltaInteresse());
                        System.out.println("Não Saber Usar: " + motivo.getNaoSaberUsar());
                        System.out.println("Não Ter Onde Acessar: " + motivo.getNaoTerOndeAcessar());
                        System.out.println("Ser Muito Caro: " + motivo.getSerMuitoCaro());
                        System.out.println("Segurança/Privacidade: " + motivo.getSegurancaPrivacidade());
                        System.out.println("Evitar Conteúdo Perigoso: " + motivo.getEvitarConteudoPerigoso());
                        System.out.println("Outro Motivo: " + motivo.getOutroMotivo());
                        System.out.println("---------------------------");
                    }
                }

            } else if (opcao == 3) {
                System.out.println("Saindo...");
                break;
            } else {
                System.out.println("Opção inválida!");
            }
        }

        sc.close();
    }
}
