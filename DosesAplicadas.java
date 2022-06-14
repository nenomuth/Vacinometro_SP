import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.text.Normalizer;
import java.text.Normalizer.Form;

public class DosesAplicadas {
    Scanner in = new Scanner(System.in);
    
    
    ArrayList<String> lista = new ArrayList<String>();
    TipoDose dose = new TipoDose();
    ArrayList<String> listaDose = new ArrayList<String>();
    

    public ArrayList<String> all(ArrayList allGeral){

        String linha;

        try{
            
            File arquivoCSV = new File("C:\\Tp2 Java\\20220525_vacinometro.csv");
            BufferedReader br = new BufferedReader(new FileReader(arquivoCSV));
    
                //Lê as linhas do arquivo enquanto não forem nulas e as separa em um Array, o valor na posição [1] do array é o nome da dose, que é colocado em uma lista, se estiver vazia
                if(allGeral.isEmpty()){
                    while ((linha = br.readLine()) != null) {
    
                        String[] colunas = linha.split(";");
    
                        //Se as colunas tiverem valor e o segundo não for "dose", adicionar na lista
                        if (colunas[0] != null && colunas[1] != null && colunas[2] != null && colunas[1].equalsIgnoreCase("dose") == false){
                            lista.add(colunas[0]);
                            lista.add(colunas[1]);
                            lista.add(colunas[2]);
                        }
                    }
                }
                
                br.close();

            }catch(FileNotFoundException e){
                System.out.println("Arquivo não encontrado");
            }catch(IOException e){
                System.out.println("Erro de leitura/escrita");
            }
            return lista;
    }


    public ArrayList<String> atualizaCidade(String cidade,String tipoDose, int j){

        boolean validador = true;
        String numDoseS;
        int numDose = 0;

        
        lista = all(lista);

            //Acho que seria possível fazer isso com índices, similar a banco de dados, nos dados das tabelas e organizar elas, mas não sei como faria.

            if(lista.get(j).equalsIgnoreCase(cidade) && lista.get(j+1).equalsIgnoreCase(tipoDose)){
                    System.out.printf("O número atual de doses dessa cidade é %s, para qual número deseja atualizar? : ",lista.get(j+2));

                        //Enquanto não for um número valido, continua requisitando um valor válido.
                        while(validador){
                                    
                        try{
                        numDose = in.nextInt();
                        }catch(InputMismatchException e){
                            System.out.println("Por favor, digite um número válido! :");
                        }
                        if (numDose > 0){
                             validador = false;
                        }
                    }
                    //Vendo agora, não faço ideia pq deixei como string, mas até agora não vi erro, então...
                    numDoseS = "" + numDose;
                    lista.set(j+2,numDoseS);

                }else{
                    System.out.println("Erro!");
                }

        return lista;

    }

    public void tudoCidade(String cidade){

        lista = all(lista);

            //Procura em cada argumento da listaGeral, todos os indexes, quando encontra um que corresponda à cidade, imprime ela, o valor dela +1 e o valor +2, que correspondem à dose e quantidade, respectivamente, até que não sejam mais encontrados valores que são iguais à cidade.
            for(int i = 0; i < lista.size()-1; i++){
                String lsNorm = Normalizer.normalize(lista.get(i), Form.NFD);
                String lsF = lsNorm.replaceAll("\\p{M}", "");
                if(cidade.equalsIgnoreCase(lsF)){
                    System.out.printf("%s == %s == %s \n",lista.get(i),lista.get(i+1),lista.get(i+2));
                }
            }
    }

    public void tudoCadastro(){

        lista = all(lista);
        System.out.println("Digite 'sair' para parar de atualizar doses, ou '0' para não atualizar as doses.");
        String c;

        //Pulando de 3 em 3, exibe a cidade, dose e quantidade, e pergunta para qual deve ser atualizado o valor, não podendo ser 0.
        for (int i = 0; i < lista.size()-3;i+=3){
            System.out.printf("Cidade: %s , Tipo: %s , Quantidade: %s , atualizar a quantidade para:",lista.get(i),lista.get(i+1),lista.get(i+2));
            c = in.nextLine();
            if(c.equalsIgnoreCase("sair")){
                System.out.println("Cancelando atualização...");
                break;
            }else if(c.equalsIgnoreCase("0")){
                System.out.println("Ignorando dose...");
            }else{
                lista.set(i+2,c);
            }
        }

    }

    public void cadastraCidDose(String cid, String dose, int qnt){

        lista = all(lista);

        //No momento em que se é cadastrada uma dose em uma cidade, é também dado um valor, então ele nunca começará como 0, prevenindo erros no método passado.
        lista.add(cid.toUpperCase());
        lista.add(dose.toUpperCase());
        String quantidade = Integer.toString(qnt);
        lista.add(quantidade);
        
    }

}
