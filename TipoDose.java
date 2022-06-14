import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.text.Collator;

public class TipoDose {

    Scanner in = new Scanner(System.in);
    ArrayList<String> dose = new ArrayList<String>();
    int validador = 0;
    String nome;
    ArrayList<String> listaDose = new ArrayList<String>();

    public void cadastroDose(){

        File arquivoCSV = new File("C:\\Tp2 Java\\20220525_vacinometro.csv");
        System.out.println("Digite a dose a ser cadastrada: ");
        nome = in.nextLine();
        int validador = 0;
        System.out.println();

        try{

            BufferedReader br = new BufferedReader(new FileReader(arquivoCSV));

            for (int i = 0; i < listaDose.size();i++){
                if(listaDose.get(i).equals(nome.toUpperCase())){
                    System.out.println("Dose já cadastrada!");
                    validador = 1;
                    //Se o validador for 1, a dose já existe, e não passará para o processo de adição de dose.
                }
            }

            if (validador == 0){
                //Adiciona a nova dose à lista e organiza a lista
                listaDose.add(nome.toUpperCase());
                Collator collator = Collator.getInstance();
		        collator.setStrength(Collator.PRIMARY);
		        Collections.sort(listaDose, collator);
                System.out.printf("Dose tipo %s cadastrada com sucesso!\n",nome.toUpperCase());
            }

            br.close();

        }catch(FileNotFoundException e){
            System.out.println("Arquivo não encontrado");
        } catch(IOException e){
            System.out.println("Erro de leitura/escrita");
        }

    }


    public ArrayList<String> listarDose(ArrayList doseGeral){

        String linha;
        ArrayList<String> nomesDose = new ArrayList<String>();

        try{

            File arquivoCSV = new File("C:\\Tp2 Java\\20220525_vacinometro.csv");
            //Coleta o arquivo para ser lido pelo programa
            BufferedReader br = new BufferedReader(new FileReader(arquivoCSV));

            //Lê as linhas do arquivo enquanto não forem nulas e as separa em um Array, o valor na posição [1] do array é o nome da dose, que é colocado em uma lista, se estiver vazia
            if(doseGeral.isEmpty()){
                while ((linha = br.readLine()) != null) {

                    String[] colunas = linha.split(";");

                    if (colunas[1] != null){
                        nomesDose.add(colunas[1]);
                    }

                }
                    nomesDose.remove(0);
                
                //Organiza os nomes das doses em ordem alfabética considerando acentuação

                Collator collator = Collator.getInstance();
		        collator.setStrength(Collator.PRIMARY);
		        Collections.sort(nomesDose, collator);

                //Comparando se o item da lista é igual ao próximo, se não for, adicionar à nova lista, gerando uma lista sem duplicatas

                for (int i = 0; i < nomesDose.size()-1; i++){

                    if ( (nomesDose.get(i).equals(nomesDose.get(i+1))) == false){
                        listaDose.add(nomesDose.get(i));
                    }
                }
                //Adicionando o último item da lista, pois ele não tem com quem ser comparado ao final
                listaDose.add(nomesDose.get(nomesDose.size()-1));

                //Fecha o BufferedReader
                br.close();
            }


        }catch(FileNotFoundException e){
            System.out.println("Arquivo não encontrado");
        }catch(IOException e){
            System.out.println("Erro de leitura/escrita");
        }

        //Se a lista não estiver vazia, o método só retorna a lista para que seja utilizada
        return(listaDose);
    }
}
