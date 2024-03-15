
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class T7Sender extends JFrame {

    private DatagramSocket socket;
    private InetAddress address;
    private byte[] buffer;
    private JTextArea textArea;
    private JTextField textField;
    private JButton sendButton;

    public T7Sender() {
        initComponents();
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("localhost");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("UDP Chat - Sender");
        setSize(400, 300);
        setLayout(null);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 10, 380, 200);
        add(scrollPane);

        textField = new JTextField();
        textField.setBounds(10, 220, 300, 30);
        add(textField);

        sendButton = new JButton("Send");
        sendButton.setBounds(320, 220, 70, 30);
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        add(sendButton);
    }

    private void sendMessage() {
        String message = textField.getText();
        buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 12345);
        try {
            socket.send(packet);
            appendToTextArea("Me: " + message);
            textField.setText("");
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
                new T7Sender().setVisible(true);
            }
        });
    }
}