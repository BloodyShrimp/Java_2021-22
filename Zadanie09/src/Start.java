import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

public class Start {
    JFrame border;
    JPanel buttons;
    JButton loadButton;
    Canvas space;

    Graph graph = new Graph();
    List<Knot> knots = new LinkedList<>();
    List<Edge> edges = new LinkedList<>();
    Map<Edge, Float> weights = new HashMap<>();

    public static void main(String[] args) {
        Start canvas = new Start();
        canvas.border = new JFrame("Graph");
        canvas.border.setSize(500, 500);
        canvas.loadButton = new JButton("Load");
        canvas.loadButton.addActionListener(canvas.new LoadFile());
        canvas.space = new Canvas(canvas.graph, canvas.knots, canvas.edges, canvas.weights);

        canvas.buttons = new JPanel(new FlowLayout());
        canvas.buttons.add(canvas.loadButton);

        canvas.border.add(BorderLayout.PAGE_START, canvas.buttons);

        canvas.border.add(BorderLayout.CENTER, canvas.space);
        canvas.border.setVisible(true);
        canvas.border.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    class LoadFile implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
            if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                readFile(file.getAbsolutePath());
                space.repaint();
            }
        }
    }

    void readFile(String file) {
        knots.clear();
        edges.clear();
        weights.clear();

        Scanner sc = null;
        try {
            sc = new Scanner(new File(file));
        } catch (Exception e) {
        }

        int knots_amount = sc.nextInt();
        for (int i = 0; i < knots_amount; i++) {
            knots.add(new Knot(sc.nextInt(), sc.nextInt()));
        }

        int edges_amount = sc.nextInt();
        for (int i = 0; i < edges_amount; i++) {
            edges.add(new Edge(knots.get(sc.nextInt() - 1), knots.get(sc.nextInt() - 1), sc.nextInt()));
        }

        sc.close();
        findCoordinates();
        normalizeWeights();
    }

    void findCoordinates() {
        graph.left = knots.get(0).x;
        graph.right = knots.get(0).x;
        graph.top = knots.get(0).y;
        graph.bottom = knots.get(0).y;

        for (var knot : knots) {
            if (knot.x < graph.left) {
                graph.left = knot.x;
            }
            if (knot.x > graph.right) {
                graph.right = knot.x;
            }
            if (knot.y < graph.bottom) {
                graph.bottom = knot.y;
            }
            if (knot.y > graph.top) {
                graph.top = knot.y;
            }
        }

        for (var knot : knots) {
            knot.x -= graph.left;
            knot.y -= graph.bottom;
        }

        graph.right -= graph.left;
        graph.top -= graph.bottom;
        graph.left -= graph.left;
        graph.bottom -= graph.bottom;

        for (var knot : knots) {
            double distance = knot.y - ((double) graph.top / 2);
            knot.y = knot.y - (int) (2 * distance);
        }
    }

    void normalizeWeights() {
        float max_weight = edges.get(0).weight;

        for (var edge : edges) {
            if (edge.weight > max_weight) {
                max_weight = edge.weight;
            }
        }

        for (var edge : edges) {
            edge.weight = (edge.weight / max_weight) * 10;
        }
    }
}

class Canvas extends JPanel {
    Graph graph;
    List<Knot> knots;
    List<Edge> edges;
    Map<Edge, Float> weights;

    public Canvas(Graph _graph, List<Knot> _knots, List<Edge> _edges, Map<Edge, Float> _weights) {
        graph = _graph;
        knots = _knots;
        edges = _edges;
        weights = _weights;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D grafika = (Graphics2D) g;
        grafika.setColor(Color.black);

        grafika.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        grafika.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int height = getHeight();
        int width = getWidth();
        int max_size = width < height ? width : height;
        int min_size = width > height ? width : height;
        int max_unit = graph.right > graph.top ? graph.right : graph.top;
        int min_unit = graph.right < graph.top ? graph.right : graph.top;
        int offset = max_unit - min_unit;
        int x_offset = 1;
        int y_offset = 1;

        if (graph.right > graph.top) {
            y_offset = 1 + (int) (offset / 2);
        } else {
            x_offset = 1 + (int) (offset / 2);
        }

        int tooWide = 0;
        int tooLong = 0;
        double unit = 1.0;

        if (graph.right != 0 && graph.top != 0) {
            unit = max_size / (max_unit + 2);
            if (width > height) {
                tooWide = (int) Math.floor((((min_size / unit) - (graph.right - graph.left)) / 2) - 0.5);
            } else if (width < height) {
                tooLong = (int) Math.floor((((min_size / unit) - (graph.top - graph.bottom)) / 2) - 0.5);
            }
        }

        float knot_width = (float) (max_size * 0.005);
        int knot_radius = (int) (max_size * 0.05);

        grafika.setStroke(new BasicStroke(knot_width));
        for (var knot : knots) {
            grafika.drawOval((int) (unit * (knot.x + x_offset + tooWide)), (int) (unit * (knot.y + y_offset + tooLong)),
                    knot_radius,
                    knot_radius);
        }
        for (var edge : edges) {
            grafika.setStroke(new BasicStroke((float) (edge.weight * max_size * 0.002)));
            grafika.drawLine((int) (unit * (edge.beg.x + x_offset + tooWide) + knot_radius / 2),
                    (int) (unit * (edge.beg.y + y_offset + tooLong) + knot_radius / 2),
                    (int) (unit * (edge.end.x + x_offset + tooWide) + knot_radius / 2),
                    (int) (unit * (edge.end.y + y_offset + tooLong) + knot_radius / 2));

        }
    }
}

class Knot {
    public int x;
    public int y;

    public Knot(int _x, int _y) {
        x = _x;
        y = _y;
    }

    public String toString() {
        return "Knot[x = " + x + ", y = " + y + "]";
    }
}

class Edge {
    Knot beg;
    Knot end;
    float weight;

    public Edge(Knot _beg, Knot _end, float _weight) {
        beg = _beg;
        end = _end;
        weight = _weight;
    }

    public String toString() {
        return "Edge[beg = " + beg + ", end = " + end + ", weight = " + weight + "]";
    }
}

class Graph {
    int left;
    int right;
    int top;
    int bottom;
}