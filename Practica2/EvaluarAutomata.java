// Copyright (c) 2025 Adrián Quiroga Linares Lectura y referencia permitidas; reutilización y plagio prohibidos

import java.util.ArrayList;

public class EvaluarAutomata {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: java EvaluarAutomata <nombre_archivo> <cadena>");
            return;
        }

        String nombreArchivo = args[0];
        Automata a = new Automata(nombreArchivo);
        String cadena = args[1];

        evaluarAutomata(a, cadena);
    }

    private static void evaluarAutomata(Automata a, String cadena) {
        String[] simbolos = cadena.split("");

        ArrayList<String> actuales = new ArrayList<>();
        String clausuraInicial = a.getClausura(a.getEstados().get(0));
        for (String estado : clausuraInicial.split("\\s+")) {
            actuales.add(estado);
        }

        System.out.println("Estado(s) inicial(es): " + actuales);

        for (String simbolo : simbolos) {
            ArrayList<String> siguientes = new ArrayList<>();
            int columnaSimbolo = a.getAlfabeto().indexOf(simbolo);
            if (columnaSimbolo == -1) {
                System.out.println("Símbolo '" + simbolo + "' no pertenece al alfabeto.");
                return;
            }

            System.out.println("Leyendo símbolo: '" + simbolo + "'");

            for (String estado : actuales) {
                int fila = a.getEstados().indexOf(estado);
                if (fila == -1) continue;
                String destinos = a.getTablaTransiciones()[fila][columnaSimbolo];
                if (destinos != null && !destinos.isEmpty()) {
                    for (String destino : destinos.trim().split("\\s+")) {
                        String clausura = a.getClausura(destino);
                        for (String c : clausura.split("\\s+")) {
                            if (!siguientes.contains(c)) {
                                siguientes.add(c);
                            }
                        }
                    }
                }
            }
            actuales = siguientes;
            System.out.println("Estado(s) después de leer: " + actuales);
            System.out.println("---------------");
        }

        boolean aceptada = false;
        for (String estado : actuales) {
            if (a.getEstadosFinales().contains(estado)) {
                aceptada = true;
                break;
            }
        }
        if (aceptada) {
            System.out.println("Cadena aceptada.");
        } else {
            System.out.println("Cadena rechazada.");
        }
    }
}
