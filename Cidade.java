import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.text.Collator;


public class Cidade{
    public String nomeCidade;
    Scanner in = new Scanner(System.in);
    String nome;
    ArrayList<String> lista = new ArrayList<String>();
    ArrayList<String> listaCidade = new ArrayList<String>();

    public void cadastrarCidade(){

        //Importa o arquivo
        File arquivoCSV = new File("C:\\Tp2 Java\\20220525_vacinometro.csv");

        //Recebe uma string que é a cidade que se deseja ser cadastrada
        System.out.println("Digite o nome da cidade a ser cadastrada: ");
        nome = in.nextLine();
        int validador = 0;
        System.out.println();

        try{

            BufferedReader br = new BufferedReader(new FileReader(arquivoCSV));

            for (int i = 0; i < listaCidade.size();i++){
                if(listaCidade.get(i).equals(nome.toUpperCase())){
                    System.out.println("Cidade já cadastrada!");
                    validador = 1;
                    //Se a cidade já tiver sido cadastrada, o validador será 1 e não irá para o processo de adição de cidade na lista.
                }
            }

            if (validador == 0){
                //Adiciona a nova cidade à lista e organiza a lista
                listaCidade.add(nome.toUpperCase());
                Collator collator = Collator.getInstance();
		        collator.setStrength(Collator.PRIMARY);
		        Collections.sort(listaCidade, collator);
                System.out.printf("%s cadastrada com sucesso!\n",nome.toUpperCase());
            }

            br.close();

        }catch(FileNotFoundException e){
            System.out.println("Arquivo não encontrado");
        } catch(IOException e){
            System.out.println("Erro de leitura/escrita");
        }

    }

    //Pega todas as cidades e retorna um ArrayList com todas, sem duplicadas, em ordem alfabética
    public ArrayList<String> listarCidade(ArrayList cidadeGeral){

        String linha;
        ArrayList<String> nomesCidades = new ArrayList<String>();

        try{

            File arquivoCSV = new File("C:\\Tp2 Java\\20220525_vacinometro.csv");
            //Coleta o arquivo para ser lido pelo programa
            BufferedReader br = new BufferedReader(new FileReader(arquivoCSV));

            //Lê as linhas do arquivo enquanto não forem nulas e as separa em um Array, o valor na posição [0] do array é o nome da cidade, que é colocado em uma lista, se estiver vazia
            if(cidadeGeral.isEmpty()){
                while ((linha = br.readLine()) != null) {

                    String[] colunas = linha.split(";");

                    if (colunas[0] != null){
                        nomesCidades.add(colunas[0]);
                    }

                }

                nomesCidades.remove(0);         
                
                //Organiza os nomes das cidades em ordem alfabética considerando acentuação

                Collator collator = Collator.getInstance();
		        collator.setStrength(Collator.PRIMARY);
		        Collections.sort(nomesCidades, collator);

                //Comparando se o item da lista é igual ao próximo, se não for, adicionar à nova lista, gerando uma lista sem duplicatas

                for (int i = 0; i < nomesCidades.size()-1; i++){

                    if ( (nomesCidades.get(i).equalsIgnoreCase(nomesCidades.get(i+1))) == false ){
                        listaCidade.add(nomesCidades.get(i));
                    }
                }

                
                //Adicionando o último item da lista, pois ele não tem com quem ser comparado ao final
                listaCidade.add(nomesCidades.get(nomesCidades.size()-1));

                //Fecha o BufferedReader
                br.close();
            }


        }catch(FileNotFoundException e){
            System.out.println("Arquivo não encontrado");
        }catch(IOException e){
            System.out.println("Erro de leitura/escrita");
        }

        //Se a lista não estiver vazia, o método só retorna a lista para que seja utilizada
        return(listaCidade);
    }
    
}
