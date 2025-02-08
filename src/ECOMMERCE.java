import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

class ECommerceApp extends JFrame {

    private JTextField searchField;
    private JButton searchButton;
    private JPanel productDisplayPanel;
    private JComboBox<String> categoryComboBox;
    private JLabel cartLabel;
    private int cartCount = 0;

    private HashMap<String, ArrayList<String>> products;

    public ECommerceApp() {
        setTitle("E-Commerce Website");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        products = new HashMap<>();
        loadProducts();

        // Search Bar
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchButtonListener());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Category Filter
        JPanel categoryPanel = new JPanel(new BorderLayout());
        categoryComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Books", "Clothing"});
        categoryComboBox.addActionListener(new CategoryComboBoxListener());
        categoryPanel.add(new JLabel("Category: "), BorderLayout.WEST);
        categoryPanel.add(categoryComboBox, BorderLayout.CENTER);

        // Product Display Panel
        productDisplayPanel = new JPanel();
        productDisplayPanel.setLayout(new GridLayout(0, 2));
        displayProducts("All");

        // Cart Information
        JPanel cartPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cartLabel = new JLabel("Cart: 0 items");
        cartPanel.add(cartLabel);

        add(searchPanel, BorderLayout.NORTH);
        add(categoryPanel, BorderLayout.WEST);
        add(new JScrollPane(productDisplayPanel), BorderLayout.CENTER);
        add(cartPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadProducts() {
        products.put("Electronics", new ArrayList<>());
        products.put("Books", new ArrayList<>());
        products.put("Clothing", new ArrayList<>());

        products.get("Electronics").add("Smartphone");
        products.get("Electronics").add("Laptop");
        products.get("Books").add("Java Programming");
        products.get("Books").add("Design Patterns");
        products.get("Clothing").add("T-Shirt");
        products.get("Clothing").add("Jeans");
    }

    private void displayProducts(String category) {
        productDisplayPanel.removeAll();
        ArrayList<String> productList;
        if (category.equals("All")) {
            productList = new ArrayList<>();
            for (ArrayList<String> list : products.values()) {
                productList.addAll(list);
            }
        } else {
            productList = products.get(category);
        }

        for (String product : productList) {
            JPanel productPanel = new JPanel(new BorderLayout());
            productPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            productPanel.add(new JLabel(product), BorderLayout.CENTER);

            JButton addButton = new JButton("Add to Cart");
            addButton.addActionListener(e -> addToCart());
            productPanel.add(addButton, BorderLayout.SOUTH);

            productDisplayPanel.add(productPanel);
        }

        productDisplayPanel.revalidate();
        productDisplayPanel.repaint();
    }

    private void addToCart() {
        cartCount++;
        cartLabel.setText("Cart: " + cartCount + " items");
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchTerm = searchField.getText().toLowerCase();
            if (!searchTerm.isEmpty()) {
                productDisplayPanel.removeAll();
                for (String category : products.keySet()) {
                    for (String product : products.get(category)) {
                        if (product.toLowerCase().contains(searchTerm)) {
                            JPanel productPanel = new JPanel(new BorderLayout());
                            productPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                            productPanel.add(new JLabel(product), BorderLayout.CENTER);

                            JButton addButton = new JButton("Add to Cart");
                            addButton.addActionListener(evt -> addToCart());
                            productPanel.add(addButton, BorderLayout.SOUTH);

                            productDisplayPanel.add(productPanel);
                        }
                    }
                }
                productDisplayPanel.revalidate();
                productDisplayPanel.repaint();
            } else {
                displayProducts("All");
            }
        }
    }

    private class CategoryComboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            displayProducts(selectedCategory);
        }
    }

    public static void main(String[] args) {
        new ECommerceApp();
    }
}
