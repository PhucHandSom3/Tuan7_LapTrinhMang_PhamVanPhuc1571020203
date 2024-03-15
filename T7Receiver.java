
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class T7Receiver extends JFrame {

    private DatagramSocket socket;
    private byte[] buffer;
    private JTextArea textArea;
    private JButton receiveButton;

    public T7Receiver() {
        initComponents();
        try {
            socket = new DatagramSocket(12345);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("UDP Chat - Receiver");
        setSize(400, 300);
        setLayout(null);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 10, 380, 220);
        add(scrollPane);

        receiveButton = new JButton("Receive");
        receiveButton.setBounds(150, 240, 100, 30);
        receiveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                receiveMessage();
            }
        });
        add(receiveButton);
    }

    private void receiveMessage() {
        buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(packet);
            String message = new String(packet.getData(), 0, packet.getLength());
            appendToTextArea("Sender: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void appendToTextArea(String message) {
        textArea.append(message + "\n");
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new T7Receiver().setVisible(true);
            }
        });
    }
}