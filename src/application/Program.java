package application;

import entities.Products;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Informe o caminho da pasta: ");
        String path = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            List<Products> list = new ArrayList<>();
            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                list.add(new Products(fields[0], Double.parseDouble(fields[1])));
                line = br.readLine();
            }
            double avg = list.stream()  //PipeLine
                    .map(p -> p.getPreco())
                    .reduce(0.0, (x, y) -> x + y) / list.size();

            System.out.println("Media do preco " + String.format("%.2f", avg));

            Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());

            List<String> nomes = list.stream()   //PipeLine
                    .filter(p -> p.getPreco() < avg)
                    .map(p -> p.getNome())
                    .sorted(comp.reversed())
                    .collect(Collectors.toList());

            nomes.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Erro" + e.getMessage());
        }
        sc.close();
    }
}
