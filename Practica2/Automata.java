// Copyright (c) 2025 Adrián Quiroga Linares Lectura y referencia permitidas; reutilización y plagio prohibidos

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Automata {
    private ArrayList<String> estados;
    private ArrayList<String> estadosFinales;
    private ArrayList<String> alfabeto;
    private String[][] tablaTransiciones;

    public Automata(String fichero) {
        estados = new ArrayList<>();
        estadosFinales = new ArrayList<>();
        alfabeto = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(fichero));
            String linea = scanner.nextLine();
            String[] partes = linea.trim().split("\\s+");
            int numEstados = Integer.parseInt(partes[0].substring(1));
            for (int i = 1; i < partes.length; i++) {
                estados.add(partes[i]);
            }

            linea = scanner.nextLine();
            partes = linea.trim().split("\\s+");
            int numEstadosFinales = Integer.parseInt(partes[0].substring(1));
            for (int i = 1; i < partes.length; i++) {
                estadosFinales.add(partes[i]);
            }

            linea = scanner.nextLine();
            partes = linea.trim().split("\\s+");
            int numAlfabeto = Integer.parseInt(partes[0].substring(1));
            for (int i = 1; i < partes.length; i++) { 
                alfabeto.add(partes[i]);
            }

            ArrayList<String[]> transiciones = new ArrayList<>();
            while (scanner.hasNextLine()) {
                linea = scanner.nextLine().trim();
                if (linea.isEmpty() || linea.startsWith("--")) continue;
                partes = linea.split("#");
                for (int i = 0; i < partes.length; i++) {
                    partes[i] = partes[i].trim();
                }
                transiciones.add(partes);
            }
            tablaTransiciones = new String[transiciones.size()][];
            for (int i = 0; i < transiciones.size(); i++) {
                tablaTransiciones[i] = transiciones.get(i);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getEstados() {
        return estados;
    }

    public ArrayList<String> getEstadosFinales() {
        return estadosFinales;
    }

    public ArrayList<String> getAlfabeto() {
        return alfabeto;
    }

    public String[][] getTablaTransiciones() {
        return tablaTransiciones;
    }

    public String getClausura(String estadoInicial) {
        ArrayList<String> clausura = new ArrayList<>();
        clausura.add(estadoInicial);

        int columnaVacia = tablaTransiciones[0].length -1 ;

        ArrayList<String> cola = new ArrayList<>();
        cola.add(estadoInicial);

        while (!cola.isEmpty()) {
            String estadoActual = cola.remove(0);
            int indiceEstado = estados.indexOf(estadoActual);
            if (indiceEstado == -1) continue;

            String destinos = tablaTransiciones[indiceEstado][columnaVacia];
            if (destinos != null && !destinos.isEmpty()) {
                String[] estadosDestinos = destinos.trim().split("\\s+");
                for (String destino : estadosDestinos) {
                    destino = destino.trim();
                    if (!destino.isEmpty() && !clausura.contains(destino)) {
                        clausura.add(destino);
                        cola.add(destino);
                    }
                }
            }
        }

        return String.join(" ", clausura);
    }

    public void imprimirTablaTransiciones() {
        System.out.println("Tabla de transiciones:");
        for (int i = 0; i < tablaTransiciones.length; i++) {
            for (int j = 0; j < tablaTransiciones[i].length; j++) {
                String valor = tablaTransiciones[i][j];
                if (valor == null || valor.isEmpty()) {
                    System.out.print(" - ");
                } else {
                    System.out.print(" " + valor + " ");
                }
            }
            System.out.println();
        }
    }
}
