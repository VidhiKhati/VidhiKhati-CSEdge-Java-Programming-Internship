import java.io.Serializable;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
public class RegistrationManager extends Frame implements ActionListener {
    private TextField tfName;
    private CheckboxGroup genderGroup;
    private Checkbox maleCheckbox, femaleCheckbox;
    private Choice cityChoice;
    private Checkbox[] hobbyCheckboxes;
    private Button btnSave, btnDisplay;
    private TextArea textArea;
    
    private ArrayList<User> users;
    private final String FILE_NAME = "registrants.txt";
    
    public RegistrationManager() {
        setTitle("User Registration");
        setLayout(new BorderLayout());
        Panel formPanel = new Panel(new GridLayout(6, 1));
        Panel namePanel = new Panel();
        namePanel.add(new Label("Name:"));
        tfName = new TextField(20);
        namePanel.add(tfName);
        formPanel.add(namePanel);
        Panel genderPanel = new Panel();
        genderPanel.add(new Label("Gender:"));
        genderGroup = new CheckboxGroup();
        maleCheckbox = new Checkbox("Male", genderGroup, false);
        femaleCheckbox = new Checkbox("Female", genderGroup, false);
        genderPanel.add(maleCheckbox);
        genderPanel.add(femaleCheckbox);
        formPanel.add(genderPanel);
        Panel cityPanel = new Panel();
        cityPanel.add(new Label("City:"));
        cityChoice = new Choice();
        cityChoice.add("New York");
        cityChoice.add("Los Angeles");
        cityChoice.add("Chicago");
        cityChoice.add("Other");
        cityPanel.add(cityChoice);
        formPanel.add(cityPanel);
        Panel hobbiesPanel = new Panel();
        hobbiesPanel.add(new Label("Hobbies:"));
        String[] hobbies = {"Reading", "Gaming", "Traveling", "Cooking"};
        hobbyCheckboxes = new Checkbox[hobbies.length];
        for (int i = 0; i < hobbies.length; i++) {
            hobbyCheckboxes[i] = new Checkbox(hobbies[i]);
            hobbiesPanel.add(hobbyCheckboxes[i]);
        }
        formPanel.add(hobbiesPanel);

        Panel buttonPanel = new Panel();
        btnSave = new Button("Save");
        btnSave.addActionListener(this);
        buttonPanel.add(btnSave);
        btnDisplay = new Button("Display Registrants");
        btnDisplay.addActionListener(this);
        buttonPanel.add(btnDisplay);
        formPanel.add(buttonPanel);
        
        add(formPanel, BorderLayout.NORTH);

        textArea = new TextArea(20, 40);
        textArea.setEditable(false);
        add(textArea, BorderLayout.CENTER);
        
        users = new ArrayList<>();
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        
        setSize(500, 500);
        setVisible(true);
    }
    
    // Event handling for buttons
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSave) {
            saveUser();
        } else if (e.getSource() == btnDisplay) {
            displayUsers();
        }
    }
    
    private void saveUser() {
        String name = tfName.getText();
        String gender = maleCheckbox.getState() ? "Male" : "Female";
        String city = cityChoice.getSelectedItem();
        
        ArrayList<String> hobbiesList = new ArrayList<>();
        for (Checkbox checkbox : hobbyCheckboxes) {
            if (checkbox.getState()) {
                hobbiesList.add(checkbox.getLabel());
            }
        }
        String[] hobbies = hobbiesList.toArray(new String[0]);
        
        User user = new User(name, gender, city, hobbies);
        users.add(user);
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
            oos.close();
            tfName.setText("");
            genderGroup.setSelectedCheckbox(null);
            cityChoice.select("New York");
            for (Checkbox checkbox : hobbyCheckboxes) {
                checkbox.setState(false);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
   
    private void displayUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            users = (ArrayList<User>) ois.readObject();
            ois.close();
            
            textArea.setText("");
            for (User user : users) {
                textArea.append("Name: " + user.getName() + "\n");
                textArea.append("Gender: " + user.getGender() + "\n");
                textArea.append("City: " + user.getCity() + "\n");
                textArea.append("Hobbies: ");
                for (String hobby : user.getHobbies()) {
                    textArea.append(hobby + ", ");
                }
                textArea.append("\n\n");
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new RegistrationManager();
    }
}
class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String gender;
    private String city;
    private String[] hobbies;
    
    public User(String name, String gender, String city, String[] hobbies) {
        this.name = name;
        this.gender = gender;
        this.city = city;
        this.hobbies = hobbies;
    }
    
    // Getters
    public String getName() {
        return name;
    }
    
    public String getGender() {
        return gender;
    }
    
    public String getCity() {
        return city;
    }
    
    public String[] getHobbies() {
        return hobbies;
    }
}

