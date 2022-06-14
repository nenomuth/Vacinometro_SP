//Feito por Juliano Barreira Zorzetto e Breno Rodrigues Muth - 2° Ciclo - ADS Vespertino - Fatec Praia Grande 2022

import java.util.Scanner;
import java.util.ArrayList;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Main {

    public static void main(String args[]) {
    
        //Instancia e define variáveis
        Scanner in = new Scanner(System.in);
        Cidade cidade = new Cidade();
        TipoDose dose = new TipoDose();
        DosesAplicadas aplicadas = new DosesAplicadas();
        Boolean validador = true;
        ArrayList<String> listaGeral = new ArrayList<String>();
        ArrayList<String> listaCidade = new ArrayList<String>();
        ArrayList<String> listaDose = new ArrayList<String>();
        //A lista está recebendo um método e referenciando a si mesma, pois quando a lista é atualizada, o método não realiza a leitura do arquivo novamente, mas sim a nova lista gerada, a não ser que esteja vazia, que então ela lê o arquivo e usa os dados do arquivo, não os modificados durante a sessão.
        listaDose = dose.listarDose(listaDose);
        listaGeral = aplicadas.all(listaGeral);
        listaCidade = cidade.listarCidade(listaCidade);



        while(validador){

            //Sistema de menu simples.

            System.out.println("O que deseja fazer? \n [1] - Cadastrar cidade\n [2] - Visualizar cidades\n [3] - Cadastrar Dose\n [4] - Visualizar Doses \n [5] - Atualizar número de uma dose específica\n [6] - Exibir Tudo\n [7] - Ver cidade e todas as suas doses\n [8] - Atualizar número de doses em sequência\n [9] - Cadastrar nova dose e quantidade em uma cidade\n [10] - Exportar TODOS os arquivos\n [0] - Sair do Programa ");
            String decisao = in.nextLine();
    
            switch (decisao){
                case "1":
                
                    //Cadastra uma cidade
                    cidade.cadastrarCidade();
                    break;

                case "2":

                    //Imprime as cidades da lista sem duplicatas
                    for (int i = 0; i < listaCidade.size() ; i++){
                        System.out.println(listaCidade.get(i));
                    }

                    break;
                
                case "3":

                    //Cadastra um novo tipo de dose
                    dose.cadastroDose();
                    break;

                case "4":

                    //Imprime as doses da lista sem duplicatas
                    System.out.println("-------------");
                    for (int i = 0; i < listaDose.size(); i++){
                        System.out.println(listaDose.get(i));
                    }
                    System.out.println("-------------");
                    break;
                    
                case "5":
                    
                    //Pergunta qual cidade, imprime os tipos de doses existentes na lista de doses, e solicita a dose a ser pesquisada
                    String cid,doseString;
                    int dosecid;
                    int val = 0, j = 0;
                    System.out.println("Qual cidade: ");
                    cid = in.nextLine();

                    //Imprime a lista de doses cadastradas
                    for(int i = 0;i < listaDose.size();i++){
                        System.out.printf("%d = %s | ",(i+1),listaDose.get(i));
                    }
                    System.out.println("\nDose: ");
                    dosecid = in.nextInt();
                    //De acordo com o número inserido, pega o item na lista correspondente ao index da string
                    doseString = listaDose.get(dosecid-1);
                    
                    //Procura se existe a informação da cidade com a dose ao lado, a partir da listaGeral
                    for (int i = 0; i < listaGeral.size()-3;i+=3){
                        if (listaGeral.get(i).equalsIgnoreCase(cid) && listaGeral.get(i+1).equalsIgnoreCase(doseString)){
                            val++;
                            j = i;
                        }
                    }

                    //Se achar, o valor será > 0
                    if (val>0){
                        listaGeral = aplicadas.atualizaCidade(cid,doseString,j);
                    }else{
                        System.out.println("-------------");
                        System.out.println("Cidade ou Dose não cadastradas!");
                        System.out.println("-------------");
                    }

                    break;
                
                case "6":

                    //A partir da listaGeral, que possui TODOS os dados em ordem de Cidade,Tipo de dose e Quantidade, exibe a cidade, o proximo valor e o próximo a esse, e então pula 3 valores no i, que representa a próxima cidade, e repete o processo.
                    for(int i = 0; i < listaGeral.size()-1; i+=3){
                        System.out.printf("Cidade: %s  Dose: %s  Quantidade: %s\n",listaGeral.get(i),listaGeral.get(i+1),listaGeral.get(i+2));
                    }

                    break;

                case "7":
                    
                    //Solicita uma string "cidade", procura na listaGeral, e passa para o método tudoCidade

                    String cid2;
                    System.out.println("Qual cidade?");
                    cid2 = in.nextLine();
                    //Tentei usar o método de normalização do java, para pesquisar cidades que possuem acentuação, mas não obtive sucesso, por algum motivo a normalização funciona com Strings pré digitadas no sistema, mas não quando passadas pelo Scanner.
                    String cid2Norm = Normalizer.normalize(cid2, Form.NFD);
                    String cid2F = cid2Norm.replaceAll("\\p{M}", "");
                    
                    //Comparando com a listaGeral
                    for (int i = 0; i < listaGeral.size()-1;i+=3){
                        String lsNorm = Normalizer.normalize(listaGeral.get(i), Form.NFD);
                        String lsF = lsNorm.replaceAll("\\p{M}", "");
                        if(lsF.equalsIgnoreCase(cid2F)){
                        aplicadas.tudoCidade(cid2F);
                        break;    
                        }
                    }
                    break;

                case "8":

                    //Atualiza doses nas cidades, e vai perguntando até que todas sejam atualizadas ou ignoradas.
                    aplicadas.tudoCadastro();
                    break;

                case "9":
        
                //Pergunta qual cidade, imprime os tipos de doses existentes na lista de doses, e solicita a quantidade a ser inserida
                String cid3,doseString2;
                int dosecid2,qnt;
                int val2 = 0;
                System.out.println("Qual cidade: ");
                cid3 = in.nextLine();
        
                //Imprime a lista de doses cadastradas
                for(int i = 0;i < listaDose.size();i++){
                    System.out.printf("%d = %s | ",(i+1),listaDose.get(i));
                }
                System.out.println("\nDose: ");
                dosecid2 = in.nextInt();
                //De acordo com o número inserido, pega o item na lista correspondente ao index da string
                doseString2 = listaDose.get(dosecid2-1);

                System.out.println("Quantidade: ");
                qnt = in.nextInt();
                
                //Procura se existe a informação da cidade com a dose ao lado, a partir da listaGeral
                for (int i = 0; i < listaGeral.size()-3;i+=3){
                    if (listaGeral.get(i).equalsIgnoreCase(cid3) && listaGeral.get(i+1).equalsIgnoreCase(doseString2)){
                        System.out.println("\n------------\nJá cadastrado!\n------------\n");
                        val2++;
                    }
                }

                if(val2 == 0){
                    aplicadas.cadastraCidDose(cid3,doseString2,qnt);
                    System.out.printf("Cidade %s, Dose: %s, Quantidade: %s\n ------------ \nCadastrados com sucesso!\n",cid3.toUpperCase(),doseString2,qnt);
                }
                //por algum motivo quando o case '9' termina, ele sempre imprime duas vezes o menu, em falha ou sucesso no cadastro.
                    break;

                case "10":

                    int val3 = 0;
                    boolean validador2 = true;
                    
                    //Tenta criar um arquivo com número, para não haver conflito ou tentar escrever em cima de um arquivo já existente, se o próximo número de arquivo estiver disponivel,cria um arquivo com o número disponível
                    while (validador2){
                    try {
                        File csvFinal = new File("csvFinal"+val3+".csv");
                        if (csvFinal.createNewFile()) {
                            System.out.println("\n------------\nArquivo Criado: " + csvFinal.getName()+"\n------------\n");
                            validador2 = false;
                        } else {
                            val3 ++;
                        }
                        }catch(IOException e){
                            System.out.println("\n------------\nOcorreu um erro!\n------------\n");
                            e.printStackTrace();
                        }
                    }

                    //Escreve no arquivo dado como disponível anteriormente, colocando o cabeçalho de "Município;Dose;Total Doses Aplicadas", e então escrevendo os dados e pulando uma linha
                    try {
                        FileWriter writer = new FileWriter("csvFinal"+val3+".csv");
                        writer.write("Município;Dose;Total Doses Aplicadas\n");
                        for(int i = 0; i < listaGeral.size()-1; i+=3){
                            writer.write(listaGeral.get(i)+";"+listaGeral.get(i+1)+";"+listaGeral.get(i+2)+"\n");
                        }
                        writer.close();
                        System.out.println("------------\nArquivo escrito com sucesso!\n------------\n");

                    }catch (IOException e){
                        System.out.println("------------\nOcorreu um erro!\n------------\n");
                        e.printStackTrace();
                    }

                    //-----------------------------------------------------------------------------------------------------------------------------------

                    val3 = 0;
                    validador2 = true;
                    //Tenta criar um arquivo com número, para não haver conflito ou tentar escrever em cima de um arquivo já existente, se o próximo número de arquivo estiver disponivel,cria um arquivo com o número disponível
                    while (validador2){
                        try {
                            File cidadesFinal = new File("cidades"+val3+".bin");
                            if (cidadesFinal.createNewFile()) {
                                System.out.println("\n------------\nArquivo Criado: " + cidadesFinal.getName()+"\n------------\n");
                                validador2 = false;
                            } else {
                                val3 ++;
                            }
                            }catch(IOException e){
                                System.out.println("\n------------\nOcorreu um erro!\n------------\n");
                                e.printStackTrace();
                            }
                        }

                        //Escreve no arquivo dado como disponível anteriormente, colocando o cabeçalho de "Município;Dose;Total Doses Aplicadas", e então escrevendo os dados e pulando uma linha
                    try {
                    FileWriter writer = new FileWriter("cidades"+val3+".bin");

                        for (int i=0; i <listaCidade.size()-1; i++){
                            String x = listaCidade.get(i);
                            StringBuffer b = new StringBuffer();
                            for (int o = 0; o < x.length(); o++){
                                //converte cada caractere para ASCII
                                int val4 = (x.charAt(o));
                
                                //Converte ASCII para binário
                                StringBuffer a = new StringBuffer();
                                while(val4>0){
                                    if(val4%2 == 0){
                                        a.append("0");
                                    }else{
                                        a.append("1");
                                    }
                                    val4 /= 2;
                                }
                
                                a.reverse();
                                b.append(a+" ");
                        
                            }
                            writer.write(b+"\n");
                        }
                    writer.close();
                    }catch (IOException e){
                        System.out.println("------------\nOcorreu um erro!\n------------\n");
                        e.printStackTrace();
                    }


                    //-----------------------------------------------------------------------------------------------------------------------------------

                    val3 = 0;
                    validador2 = true;
                    //Tenta criar um arquivo com número, para não haver conflito ou tentar escrever em cima de um arquivo já existente, se o próximo número de arquivo estiver disponivel,cria um arquivo com o número disponível
                    while (validador2){
                        try {
                            File dosesFinal = new File("doses"+val3+".bin");
                            if (dosesFinal.createNewFile()) {
                                System.out.println("\n------------\nArquivo Criado: " + dosesFinal.getName()+"\n------------\n");
                                validador2 = false;
                            } else {
                                val3 ++;
                            }
                            }catch(IOException e){
                                System.out.println("\n------------\nOcorreu um erro!\n------------\n");
                                e.printStackTrace();
                            }
                        }

                    //Escreve no arquivo dado como disponível anteriormente, colocando o cabeçalho de "Município;Dose;Total Doses Aplicadas", e então escrevendo os dados e pulando uma linha
                    try {
                    FileWriter writer = new FileWriter("doses"+val3+".bin");
                        for (int i=0; i <listaDose.size(); i++){
                            String x = listaDose.get(i);
                            StringBuffer b = new StringBuffer();
                            
                            for (int z=0; z <x.length(); z++){
                                //Captura o valor inteiro de cada caractere na string
                                int val4 = (x.charAt(z));
                                //Converte ASCII para binário
                                
                                StringBuffer a = new StringBuffer();
                                while(val4>0){
                                    if(val4%2 == 0){
                                        a.append("0");
                                    }else{
                                        a.append("1");
                                    }
                                    val4 /= 2;
                                }
                
                                a.reverse();
                                
                                b.append(a+" ");
                            }
                            writer.write(b+"\n");
                        }
                    writer.close();
                        
                    }catch (IOException e){
                        System.out.println("------------\nOcorreu um erro!\n------------\n");
                        e.printStackTrace();
                    }

                    //-----------------------------------------------------------------------------------------------------------------------------------

                    val3 = 0;
                    validador2 = true;
                    //Tenta criar um arquivo com número, para não haver conflito ou tentar escrever em cima de um arquivo já existente, se o próximo número de arquivo estiver disponivel,cria um arquivo com o número disponível
                    while (validador2){
                        try {
                            File aplicadasFinal = new File("aplicadas"+val3+".bin");
                            if (aplicadasFinal.createNewFile()) {
                                System.out.println("\n------------\nArquivo Criado: " + aplicadasFinal.getName()+"\n------------\n");
                                validador2 = false;
                            } else {
                                val3 ++;
                            }
                            }catch(IOException e){
                                System.out.println("\n------------\nOcorreu um erro!\n------------\n");
                                e.printStackTrace();
                            }
                        }

                    //Escreve no arquivo dado como disponível anteriormente, colocando o cabeçalho de "Município;Dose;Total Doses Aplicadas", e então escrevendo os dados e pulando uma linha
                    try {
                    FileWriter writer = new FileWriter("aplicadas"+val3+".bin");
                        for (int i=0; i <listaGeral.size(); i++){
                            String x = listaGeral.get(i);
                            StringBuffer b = new StringBuffer();
                            
                            for (int z=0; z <x.length(); z++){
                                //Captura o valor inteiro de cada caractere na string
                                int val4 = (x.charAt(z));
                                //Converte ASCII para binário
                                
                                StringBuffer a = new StringBuffer();
                                while(val4>0){
                                    if(val4%2 == 0){
                                        a.append("0");
                                    }else{
                                        a.append("1");
                                    }
                                    val4 /= 2;
                                }
                
                                a.reverse();
                                
                                b.append(a+" ");
                            }
                            writer.write(b+"\n");
                        }
                    writer.close();
                        
                    }catch (IOException e){
                        System.out.println("------------\nOcorreu um erro!\n------------\n");
                        e.printStackTrace();
                    }
                    
                    break;

                case "0":
                    System.out.println("\nObrigado! Feito por Breno Rodrigues Muth e Juliano Barreira Zorzetto - 2° Ciclo ADS - Vespertino");
                    validador = false;
                    break;
            }
        }

        in.close();

        
    }
}
