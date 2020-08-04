/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajedrez.controlador;

import ajedrez.modelo.Ficha;
import ajedrez.modelo.Jugador;
import ajedrez.modelo.Tablero;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Sebastian Salinas
 */
public class Juego {
    
    public static Jugador j1,j2;
    public static Tablero t;
    public boolean jaque_j1 = false;
    public boolean jaque_j2 = false;
    public boolean mate_j1 = false;
    public boolean mate_j2 = false;
    
    public void iniciarJuego(){
        t = new Tablero();
        j1 = new Jugador(true);
        j2 = new Jugador(false);
        t.imprimirTablero();
       
        while(!mate_j1 || !mate_j2){
            Scanner n = new Scanner(System.in);
            Ficha ReyBlanco = t.getRey("blanco");
            Ficha ReyNegro = t.getRey("negro");
            hayJaque(ReyBlanco);
            hayJaque(ReyNegro);
            
            //Las coordenadas se ponen según la posición de la ficha: x = vertical, y = horizontal
            
            System.out.println("Ficha a mover, jugador 1");
  
            //Movimientos para el jugador 1
            int j1_x = n.nextInt();
            int j1_y = n.nextInt();
            
            while(j1_x < 0 || j1_x > 7 || j1_y < 0 || j1_y > 7){
                System.out.println("Esas coordenadas no existen...");
                j1_x = n.nextInt();
                j1_y = n.nextInt();
           }
            
            if(j1_x == -1 && j1_y==-1) break;
            Ficha verf_j1 = t.getFicha(new Point(j1_x,j1_y));
          
            while(!verf_j1.getColor().equals("blanco")){
                System.out.println("Esa ficha no es del jugador 1...");
                j1_x = n.nextInt();
                j1_y = n.nextInt();          
                verf_j1 = t.getFicha(new Point(j1_x,j1_y));
                }
            
            System.out.println("Movimientos disponibles:");
            ArrayList<Point> pmvs_j1 = t.mirarMovimientos(j1_x, j1_y, t);
            while(pmvs_j1.isEmpty()){
                System.out.println("Esa ficha no tiene movimientos disponibles...");
                j1_x = n.nextInt();
                j1_y = n.nextInt();
                verf_j1 = t.getFicha(new Point(j1_x,j1_y));
                System.out.println("Movimientos disponibles:");
                pmvs_j1 = t.mirarMovimientos(j1_x, j1_y, t);
            }
            
            int j1_x2 = n.nextInt();
            int j1_y2 = n.nextInt();
        
            moverFichaEnTablero(new Point(j1_x2,j1_y2),verf_j1);
            
            System.out.println("Ficha a mover, jugador 2");

            //Movimientos jugador 2
            int j2_x = n.nextInt();
            int j2_y = n.nextInt(); 
            
            while(j2_x < 0 || j2_x > 7 || j2_y < 0 || j2_y > 7){
                System.out.println("Esas coordenadas no existen...");
                j2_x = n.nextInt();
                j2_y = n.nextInt();
             }
            
            if(j2_x == -1 && j2_y==-1) break;
            Ficha verf_j2 = t.getFicha(new Point(j2_x,j2_y));
      
            while(!verf_j2.getColor().equals("negro")){
                System.out.println("Esa ficha no es del jugador 2...");
                j2_x = n.nextInt();
                j2_y = n.nextInt();          
                verf_j2 = t.getFicha(new Point(j2_x,j2_y));
                }
                   
            System.out.println("Movimientos disponibles:");
            ArrayList<Point> pmvs_j2 = t.mirarMovimientos(j2_x, j2_y, t);
            while(pmvs_j2.isEmpty()){
                System.out.println("Esa ficha no tiene movimientos disponibles...");
                j2_x = n.nextInt();
                j2_y = n.nextInt();
                verf_j2 = t.getFicha(new Point(j2_x,j2_y));
                System.out.println("Movimientos disponibles:");
                pmvs_j2 = t.mirarMovimientos(j2_x, j2_y, t);
            }
            
            int j2_x2 = n.nextInt();
            int j2_y2 = n.nextInt();
            moverFichaEnTablero(new Point(j2_x2,j2_y2),verf_j2);

            t.imprimirTablero();
        }   
    }
     
    public static void imprimirTablero(){
        t.imprimirTablero();
    }
    
    public static void colocarFichaEnTablero(Point p, Ficha f){
        t.colocarFicha(p, f);
    }
    
    public static void moverFichaEnTablero(Point p, Ficha f){
        Point pos = f.getPosicion();
        ArrayList<Point> pmvs = t.mirarMovimientos(pos.x, pos.y, t);
        for(Point pt: pmvs){
            if(pt.equals(p)){
                t.colocarFicha(p, f);
                System.out.println("Ficha colocada en:" + p);
                t.removerFicha(f.getPosicion(), f);
                System.out.println("Ficha removida de:"+ f.getPosicion());
                t.actualizarPosFicha(p);
                break;
            }
            else{
                //do nothing :v
            }
        }
    
    }
    
    public boolean hayJaque(Ficha Rey){
        HashMap<Point,ArrayList> pmov = new HashMap<>();
        ArrayList<Point> totalmov = new ArrayList<>();
        Point posRey = Rey.getPosicion();
        if(Rey.getColor().equals("blanco")){
            pmov = t.movimientosJugador("negro");
            for(ArrayList p: pmov.values()){         
                for(Object pt: p){
                    totalmov.add((Point) pt);
                }
            }
        }
        else{
            pmov = t.movimientosJugador("blanco");
            for(ArrayList p: pmov.values()){         
                for(Object pt: p){
                    totalmov.add((Point) pt);
                }
            }        
        }
        
        if(totalmov.contains(posRey)){
            System.out.println("El rey del jugador "+Rey.getColor()+" se encuentra en jaque");
            return true;
        }
        return false;
    }
    
}
