import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

class Vertex implements Comparable<Vertex>
{
    public final String name;
    public Edge[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;
    public Vertex(String argName) { name = argName; }
    public String toString() { return name; }
    public int compareTo(Vertex other)
    {
        return Double.compare(minDistance, other.minDistance);
    }

}


class Edge
{
    public final Vertex target;
    public final double weight;
    public Edge(Vertex argTarget, double argWeight)
    { 
        target = argTarget; weight = argWeight; }
}

public class Dijkstra
{
    public static void computePaths(Vertex source)
    {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
    vertexQueue.add(source);

    while (!vertexQueue.isEmpty()) {
        Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies)
            {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
        if (distanceThroughU < v.minDistance) {
            vertexQueue.remove(v);

            v.minDistance = distanceThroughU ;
            v.previous = u;
            vertexQueue.add(v);
        }
            }
        }
    }

    public static List<Vertex> getShortestPathTo(Vertex target)
    {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);

        Collections.reverse(path);
        return path;
    }

    public static String doit(String start,String end)
    {
        // mark all the vertices 
        Vertex A = new Vertex("LBS");
        Vertex B = new Vertex("MMM");
        Vertex C = new Vertex("LLR");
        Vertex D = new Vertex("AZAD");
        Vertex E = new Vertex("NEHRU");
        Vertex F = new Vertex("PATEL");
        Vertex G = new Vertex("BC ROY HOSPITAL");
        Vertex H = new Vertex("BRH");
        Vertex I = new Vertex("PAN LOOP");
        Vertex J = new Vertex("DIRECTOR's HOUSE");
        Vertex K = new Vertex("NALANDA COMPLEX");
        Vertex L = new Vertex("VIKRAMSHILA");
        Vertex M = new Vertex("MUSEUM");
        Vertex N = new Vertex("VGSOM");
        Vertex O = new Vertex("ARENA");
        Vertex P = new Vertex("SBI BANK");
        Vertex Q = new Vertex("TIKKA");
        Vertex R = new Vertex("SN");
        Vertex S = new Vertex("MT");
        Vertex T = new Vertex("GYMKHANA");
        Vertex U = new Vertex("BASKETBALL COURT");
        Vertex V = new Vertex("TENIS COURT");
        Vertex W = new Vertex("GYNAN GHOSH STADIUM");
        Vertex X = new Vertex("MAHATAMA GHANDI STADIUM");
        Vertex Y = new Vertex("TATA STEEL COMPLEX");
        Vertex Z = new Vertex("RP");
        Vertex Aa = new Vertex("RK");
        Vertex Ab = new Vertex("MS");
        Vertex Ac = new Vertex("TECH MARKET");
        Vertex Ad = new Vertex("MAIN BUILDING");
        Vertex Ae = new Vertex("CENTRAL 4 WAY");
        // Vertex Ac = new Vertex("PATEL");
A.adjacencies = new Edge[]{ new Edge(B, 1) , new Edge(I,3) };
        B.adjacencies = new Edge[]{ new Edge(A, 1), new Edge(C,1)};
        C.adjacencies = new Edge[]{ new Edge(B, 1),new Edge(U,1) };
        D.adjacencies = new Edge[]{ new Edge(I, 3),new Edge(G,2) };
        E.adjacencies = new Edge[]{ new Edge(F, 1) };
        F.adjacencies = new Edge[]{ new Edge(I, 1),new Edge(E, 1) };
        G.adjacencies = new Edge[]{ new Edge(D, 2), new Edge(Ac, 8),new Edge(T, 6),new Edge(G, 10) };
        H.adjacencies = new Edge[]{ new Edge(Ac, 2) };
        I.adjacencies = new Edge[]{ new Edge(A, 3),new Edge(F, 1),new Edge(V, 2),new Edge(D, 3),new Edge(T, 4) };
        J.adjacencies = new Edge[]{ new Edge(Ac, 8),new Edge(G, 10),new Edge(R, 4),new Edge(S, 4),new Edge(T, 5) };
        K.adjacencies = new Edge[]{ new Edge(X, 1),new Edge(M, 8),new Edge(L, 11) };
        L.adjacencies = new Edge[]{ new Edge(M, 7),new Edge(K, 11),new Edge(N, 6),new Edge(Y, 6),new Edge(O, 5)};
        M.adjacencies = new Edge[]{ new Edge(N, 2),new Edge(K, 8) ,new Edge(L, 7)};
        N.adjacencies = new Edge[]{ new Edge(L, 6),new Edge(M, 2),new Edge(O, 5) };
        O.adjacencies = new Edge[]{ new Edge(Y, 3),new Edge(N, 5),new Edge(L, 5) ,new Edge(Ad, 1)};
        P.adjacencies = new Edge[]{ new Edge(Z, 3),new Edge(Ae, 5) };
        Q.adjacencies = new Edge[]{ new Edge(Y, 0.5),new Edge(R, 1),new Edge(S, 1),new Edge(Ae, 3.2) };
        R.adjacencies = new Edge[]{ new Edge(J, 4),new Edge(S, 0.3),new Edge(Q, 1),new Edge(Ae, 3) };
        S.adjacencies = new Edge[]{ new Edge(R, 0.3),new Edge(J, 4),new Edge(Q, 1) ,new Edge(Ae, 3)};
        T.adjacencies = new Edge[]{ new Edge(J, 5),new Edge(U, 2),new Edge(V, 2),new Edge(I, 4),new Edge(G, 6),new Edge(Ac, 8),new Edge(W, 1) };
        U.adjacencies = new Edge[]{ new Edge(T, 2),new Edge(Ab, 1),new Edge(C, 1) };
        V.adjacencies = new Edge[]{ new Edge(T, 2),new Edge(I, 2) };
        W.adjacencies = new Edge[]{ new Edge(T, 1),new Edge(Ae, 3.9) };
        X.adjacencies = new Edge[]{ new Edge(K, 1) };
        Y.adjacencies = new Edge[]{ new Edge(Q, 0.5),new Edge(O, 3),new Edge(L, 6) };
        Z.adjacencies = new Edge[]{ new Edge(Aa, 0.5),new Edge(Ae, 2.9),new Edge(P, 3) };
        Aa.adjacencies = new Edge[]{ new Edge(Z, 0.5),new Edge(Ab, 3) };
        Ab.adjacencies = new Edge[]{ new Edge(U, 1),new Edge(Aa, 3) };
        Ac.adjacencies = new Edge[]{ new Edge(G, 8),new Edge(H, 2),new Edge(J, 8),new Edge(T, 8) };
        Ad.adjacencies = new Edge[]{ new Edge(O, 1),new Edge(Ae, 0.1) };
        Ae.adjacencies = new Edge[]{ new Edge(P, 5),new Edge(Z, 2.9),new Edge(Q, 3.2),new Edge(R, 3),new Edge(S, 3),new Edge(W, 3.9),new Edge(Ad, 0.1) };
        
        // set the edges and weight
        
        if(start.equals("LBS"))
            computePaths(A);
        else if(start.equals("MMM"))
            computePaths(B);
        else if(start.equals("LLR"))
            computePaths(C);
        else if(start.equals("CENTRAL 4 WAY"))
            computePaths(Ae);
        else if(start.equals("AZAD"))
            computePaths(D);
        else if(start.equals("NEHRU"))
            computePaths(E);
        else if(start.equals("PATEL"))
            computePaths(F);
        else if(start.equals("BC ROY HOSPITAL"))
            computePaths(G);
        else if(start.equals("BRH"))
            computePaths(H);
        else if(start.equals("PAN LOOP"))
            computePaths(I);
        else if(start.equals("DIRECTOR's HOUSE"))
            computePaths(J);
        else if(start.equals("NALANDA COMPLEX"))
            computePaths(K);
        else if(start.equals("VIKRAMSHILA"))
            computePaths(L);
        else if(start.equals("MUSEUM"))
            computePaths(M);
        else if(start.equals("VGSOM"))
            computePaths(N);
        else if(start.equals("ARENA"))
            computePaths(O);
        else if(start.equals("SBI BANK"))
            computePaths(P);
        else if(start.equals("TIKKA"))
            computePaths(Q);
        else if(start.equals("SN"))
            computePaths(R);
        else if(start.equals("MT"))
            computePaths(S);
        else if(start.equals("GYMKHANA"))
            computePaths(T);
        else if(start.equals("BASKETBALL COURT"))
            computePaths(U);
        else if(start.equals("TENIS COURT"))
            computePaths(V);
        else if(start.equals("GYNAN GHOSH STADIUM"))
            computePaths(W);
        else if(start.equals("MAHATAMA GHANDI STADIUM"))
            computePaths(X);
        else if(start.equals("TATA STEEL COMPLEX"))
            computePaths(Y);
        else if(start.equals("RP"))
            computePaths(Z);
        else if(start.equals("RK"))
            computePaths(Aa);
        else if(start.equals("MS"))
            computePaths(Ab);
        else if(start.equals("TECH MARKET"))
            computePaths(Ac);
        else if(start.equals("MAIN BUILDING"))
            computePaths(Ad);
        else{}
        List<Vertex> path = null;
        if(end.equals("LBS"))
            path=getShortestPathTo(A);
        else if(end.equals("MMM"))
            path=getShortestPathTo(B);
        else if(end.equals("LLR"))
            path=getShortestPathTo(C);
        else if(end.equals("AZAD"))
            path=getShortestPathTo(D);
         else if(start.equals("CENTRAL 4 WAY"))
            path=getShortestPathTo(Ae);
        else if(end.equals("NEHRU"))
            path=getShortestPathTo(E);
        else if(end.equals("PATEL"))
            path=getShortestPathTo(F);
        else if(end.equals("BC ROY HOSPITAL"))
            path=getShortestPathTo(G);
        else if(end.equals("BRH"))
            path=getShortestPathTo(H);
        else if(end.equals("PAN LOOP"))
            path=getShortestPathTo(I);
        else if(end.equals("DIRECTOR's HOUSE"))
            path=getShortestPathTo(J);
        else if(end.equals("NALANDA COMPLEX"))
            path=getShortestPathTo(K);
        else if(end.equals("VIKRAMSHILA"))
            path=getShortestPathTo(L);
        else if(end.equals("MUSEUM"))
            path=getShortestPathTo(M);
        else if(end.equals("VGSOM"))
            path=getShortestPathTo(N);
        else if(end.equals("ARENA"))
            path=getShortestPathTo(O);
        else if(end.equals("SBI BANK"))
            path=getShortestPathTo(P);
        else if(end.equals("TIKKA"))
            path=getShortestPathTo(Q);
        else if(end.equals("SN"))
            path=getShortestPathTo(R);
        else if(end.equals("MT"))
            path=getShortestPathTo(S);
        else if(end.equals("GYMKHANA"))
            path=getShortestPathTo(T);
        else if(end.equals("BASKETBALL COURT"))
            path=getShortestPathTo(U);
        else if(end.equals("TENIS COURT"))
            path=getShortestPathTo(V);
        else if(end.equals("GYNAN GHOSH STADIUM"))
            path=getShortestPathTo(W);
        else if(end.equals("MAHATAMA GHANDI STADIUM"))
            path=getShortestPathTo(X);
        else if(end.equals("TATA STEEL COMPLEX"))
            path=getShortestPathTo(Y);
        else if(end.equals("RP"))
            path=getShortestPathTo(Z);
        else if(end.equals("RK"))
            path=getShortestPathTo(Aa);
        else if(end.equals("MS"))
            path=getShortestPathTo(Ab);
        else if(end.equals("TECH MARKET"))
            path=getShortestPathTo(Ac);
        else if(end.equals("MAIN BUILDING"))
            path=getShortestPathTo(Ad);
        else{} 
        JOptionPane.showMessageDialog(null,"5");
//computePaths();
//        System.out.println("Distance to " + Ad + ": " + Ad.minDistance);
//        List<Vertex> path = getShortestPathTo(L);//end
//        System.out.println("Path: " + path);
        return (""+path);
    }
    public static void main(String[] args){
        doit("LBS","MAIN BUILDING");
    }
}


