/**
 * 
 */
package finance_app_package;

//imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
/**
 *
 */

class LoginPage extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PlaceholderTextField username;
	JPasswordField password;
	JPasswordField retype_password;
	JButton login, login2;
	JButton register, register2;
	String filename = "user_info.txt";
	JPanel login_panel;
	FinancialManager finance_page;
	LoginPage(){
		
		login_panel = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Login");
		//declare buttons
		Dimension field_size = new Dimension(400, 30);
		username = new PlaceholderTextField("Your Username");
		username.setPreferredSize(field_size);
		username.setMaximumSize(field_size);
		password = new JPasswordField();
		password.setPreferredSize(field_size);
		password.setMaximumSize(field_size);
		retype_password = new JPasswordField();
		retype_password.setPreferredSize(field_size);
		retype_password.setMaximumSize(field_size);
		
		Dimension butt_size = new Dimension(100, 40);
		login = new JButton("Login");
		login.setPreferredSize(butt_size);
		login.setMaximumSize(butt_size);
		login2 = new JButton("Login");
		login2.setPreferredSize(butt_size);
		login2.setMaximumSize(butt_size);
		register = new JButton("Register");
		register.setPreferredSize(butt_size);
		register.setMaximumSize(butt_size);
		register2 = new JButton("Register");
		register2.setPreferredSize(butt_size);
		register2.setMaximumSize(butt_size);
		
		//declare labels
		JLabel title = new JLabel("Finance Manager");
		title.setFont(new Font("Aerial", Font.BOLD, 20));
		JLabel instr = new JLabel("Enter your Credentials");
		instr.setFont(new Font("Aerial", Font.BOLD, 16));
		
		Dimension txt_size = new Dimension(120, 40);
		JLabel user_txt = new JLabel("User Name: ");
		JLabel pss_text = new JLabel("Password: ");
		JLabel retype_pss_txt = new JLabel("Retype Password: ");
		JLabel succ_mess = new JLabel("Successfully registered your account. Login to start appliciation");
		user_txt.setPreferredSize(txt_size);
		user_txt.setMaximumSize(txt_size);
		pss_text.setPreferredSize(txt_size);
		pss_text.setMaximumSize(txt_size);
		retype_pss_txt.setPreferredSize(txt_size);
		retype_pss_txt.setMaximumSize(txt_size);
		succ_mess.setPreferredSize(new Dimension(400, 40));
		succ_mess.setMaximumSize(new Dimension(400, 40));
		succ_mess.setForeground(Color.green);
		
		
		JPanel usrname_panel = this.getTxtFieldsPanel2(user_txt, username, 0);
		JPanel pass_panel = this.getTxtFieldsPanel(pss_text, password, 0);
		JPanel retype_pass_panel = this.getTxtFieldsPanel(retype_pss_txt, retype_password, 0);
		retype_pass_panel.setVisible(false);
		JPanel succ_mess_panel = new JPanel();
		succ_mess_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		succ_mess_panel.add(succ_mess);
		succ_mess_panel.setVisible(false);
		
		
		//edit components
		title.setAlignmentX(CENTER_ALIGNMENT);
		instr.setAlignmentX(CENTER_ALIGNMENT);
		login.setAlignmentX(CENTER_ALIGNMENT);
		register.setAlignmentX(CENTER_ALIGNMENT);
		
		JPanel us_pss = new JPanel();
		us_pss.setLayout(new BoxLayout(us_pss, BoxLayout.Y_AXIS));
		//add username row
		us_pss.add(usrname_panel);
		//add gap
		us_pss.add(Box.createRigidArea(new Dimension(0, 20)));
		//add pss row
		us_pss.add(pass_panel);
		//add gap
		us_pss.add(Box.createRigidArea(new Dimension(0, 20)));
		//add retype password panel
		us_pss.add(retype_pass_panel);
		
		//title
		login_panel.add(title);
		login_panel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		//instructions
		login_panel.add(instr);
		login_panel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		//add username and password panel
		login_panel.add(us_pss);
		
		//create combine panel of buttons to attach them in single row
		//buttons for login page
		JPanel buttons = new JPanel();
		JPanel buttons2 = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		buttons.add(Box.createRigidArea(new Dimension(100, 0)));
		buttons.add(login);
		buttons.add(Box.createRigidArea(new Dimension(140, 0)));
		buttons.add(register);
		
		//buttons for register page
		buttons2.setLayout(new BoxLayout(buttons2, BoxLayout.X_AXIS));
		buttons2.setVisible(false);
		buttons2.add(Box.createRigidArea(new Dimension(150, 0)));
		buttons2.add(register2);
		buttons2.add(Box.createRigidArea(new Dimension(100, 0)));
		buttons2.add(login2);
		
		//set listeners of login
		ActionListener login_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//disappear succ_mess
				succ_mess_panel.setVisible(false);
				//verify availability of input data
				if((username.getText().equals("")) || (new String(password.getPassword()).equals(""))){
					JOptionPane.showMessageDialog(null, "Kindly fill all fields", "Value Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				//verify input data
				try {
					BufferedReader file_reader = new BufferedReader(new FileReader(filename));
					String txt;
					boolean is_find= false;
					int ind = 0;
					while((txt = file_reader.readLine()) != null) {
						String[] fields = txt.split(",");
						if ((username.getText().equals(fields[0])) && (new String(password.getPassword()).equals(fields[1]))) {
							System.out.println(ind);
							finance_page = new FinancialManager(LoginPage.this, ind, filename);
							finance_page.Open();
							finance_page.setVisibility(true);
							login_panel.setVisible(false);
							is_find = true;
							LoginPage.this.setTitle("Personal Finance Manager");
						}
						ind += 1;
					}
					if(is_find == false) {
						JOptionPane.showMessageDialog(null, "Incorrect Credentials", "Exception", JOptionPane.ERROR_MESSAGE);
					}
					file_reader.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Can't verify data", "Exception", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e2) {
					JOptionPane.showMessageDialog(null, "Can't verify data", "Exception", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
//		
		ActionListener register_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				succ_mess_panel.setVisible(false);
				//check if fields are filled
				if((username.getText().equals("")) || (new String(password.getPassword()).equals("")) || (new String(retype_password.getPassword()).equals(""))){
						JOptionPane.showMessageDialog(null, "Kindly fill all fields", "Value Error", JOptionPane.ERROR_MESSAGE);
						return;
				}
				else if(!(Arrays.equals(password.getPassword(), retype_password.getPassword()))) {
					JOptionPane.showMessageDialog(null, "Passwords do not match", "Password Mismatch", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				boolean is_choosed = false;
				try {
					//create file reader
					BufferedReader file_reader = new BufferedReader(new FileReader(filename));
					String inp = "";
					//read lines from file
					while((inp = file_reader.readLine()) != null) {
						//get username of users
						inp = inp.split(",")[0];
						//match usernames with entered username
						if (inp == username.getText()) {
							is_choosed = true;
						}
					}
					file_reader.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null,  "Validation Error", "Exception", JOptionPane.ERROR_MESSAGE);
					return;
				} catch (IOException io_e) {
					JOptionPane.showMessageDialog(null,  "Validation Error", "Exception", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(is_choosed == true) {
					JOptionPane.showMessageDialog(null, "Username already choosen", "Username Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					try {
						BufferedWriter file_writer = new BufferedWriter(new FileWriter(filename, true));
						file_writer.write(username.getText() + "," + new String(password.getPassword()) + ",0,0,0,0,0,0" + "\n");
						username.setText("");
						password.setText("");
						retype_password.setText("");
						succ_mess_panel.setVisible(true);
						file_writer.close();
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null,  "Can't save information", "Exception", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		};
		
		ActionListener registerSwap = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//remove success message
				succ_mess_panel.setVisible(false);
				retype_pass_panel.setVisible(true);
				buttons.setVisible(false);
				buttons2.setVisible(true);
			}
		};
		
		ActionListener loginSwap = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//remove success message
				succ_mess_panel.setVisible(false);
				retype_pass_panel.setVisible(false);
				buttons2.setVisible(false);
				buttons.setVisible(true);
			}
		};
		
		//add listeners to buttons
		login.addActionListener(login_listener);
		register2.addActionListener(register_listener);
		register.addActionListener(registerSwap);
		login2.addActionListener(loginSwap);
		
		//add button
		login_panel.add(buttons);
		login_panel.add(buttons2);
		login_panel.add(Box.createRigidArea(new Dimension(0, 40)));
		login_panel.add(succ_mess_panel);
		
		//set layout of login page
		login_panel.setLayout(new BoxLayout(login_panel, BoxLayout.Y_AXIS));
		
		setSize(600, 600);
		this.add(login_panel);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
	}
	
	public JPanel getTxtFieldsPanel(JLabel label, JPasswordField field, int dist) {
		JPanel new_panel = new JPanel();
		new_panel.setLayout(new BoxLayout(new_panel, BoxLayout.X_AXIS));
		
		//edit field
		Dimension field_size = new Dimension(400, 30);
		field.setFont(new Font("Aerial", Font.PLAIN, 16));
		field.setPreferredSize(field_size);
		field.setMaximumSize(field_size);
		
		new_panel.add(label);
		new_panel.add(Box.createRigidArea(new Dimension(dist, 0)));
		new_panel.add(field);
		
		return new_panel;
	}
	public JPanel getTxtFieldsPanel2(JLabel label, PlaceholderTextField field, int dist) {
		JPanel new_panel = new JPanel();
		new_panel.setLayout(new BoxLayout(new_panel, BoxLayout.X_AXIS));
		
		//edit field
		Dimension field_size = new Dimension(400, 30);
		field.setFont(new Font("Aerial", Font.PLAIN, 16));
		field.setPreferredSize(field_size);
		field.setMaximumSize(field_size);
		
		new_panel.add(label);
		new_panel.add(Box.createRigidArea(new Dimension(dist, 0)));
		new_panel.add(field);
		
		return new_panel;
	}
	public void clearLoginPage() {
		login_panel.setVisible(false);
	}
}

class FinancialManager extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel title;
	JFrame frame;
	Float t_money = 0f, t_expense = 0f, savings = 0f, t_liability = 0f;
	Float perm_expense = 0f, month_pay = 0f;;
	JLabel TMoney_value, TExpense_value, TSaving_value, TLiability_value;
	JTextField value;
	int file_index = 0;
	String filename, username;
	File report_info = null;
	HashMap<String, Float> source_info = new HashMap<>();
	FinancialManager(LoginPage frame, int i, String filename){
		
		//define TMoney
		TMoney_value = new JLabel(t_money.toString());
		TMoney_value.setAlignmentX(CENTER_ALIGNMENT);
		TMoney_value.setForeground(Color.green);
		
		//set TExpense
		TExpense_value = new JLabel(t_expense.toString());
		TExpense_value.setAlignmentX(CENTER_ALIGNMENT);
		TExpense_value.setForeground(Color.red);
		
		//set TSaving
		TSaving_value = new JLabel(savings.toString());
		TSaving_value.setAlignmentX(CENTER_ALIGNMENT);
		TSaving_value.setForeground(Color.yellow);
		
		TLiability_value = new JLabel(t_liability.toString());
		TLiability_value.setAlignmentX(CENTER_ALIGNMENT);
		TLiability_value.setForeground(Color.orange);
		
		this.frame = frame;
		frame.clearLoginPage();
		frame.setTitle("Financial Manager");
		file_index = i;
		this.filename = filename;
		
		//extract information from file
		try {
			BufferedReader filereader = new BufferedReader(new FileReader(filename));
			int ind = 0;
			String line;
			while((line = filereader.readLine()) != null) {
				String[] txt = line.split(",");
				if(ind == file_index) {
					username = txt[0];
					t_money = Float.parseFloat(txt[2]);
					t_expense = Float.parseFloat(txt[3]);
					savings = Float.parseFloat(txt[4]);
					t_liability = Float.parseFloat(txt[5]);
					perm_expense = Float.parseFloat(txt[6]);
					month_pay = Float.parseFloat(txt[7]);
					TMoney_value.setText(t_money.toString());
					TExpense_value.setText(t_expense.toString());
					TSaving_value.setText(savings.toString());
					TLiability_value.setText(t_liability.toString());
					break;
				}
				ind += 1;
			}
			filereader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Can't extract your information", "Exception", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Can't extract your information", "Exception", JOptionPane.ERROR_MESSAGE);
		}
		
		//create user personal information file
		report_info = new File(username + "info.txt");
		try {
			if(report_info.exists() && !report_info.isDirectory()) {
				//get previous data to initialize source_info
				ObjectInputStream filereader = new ObjectInputStream(new FileInputStream(report_info));
				String txt = (String)filereader.readObject();
				HashMap<String, Float> temp = (HashMap<String, Float>)filereader.readObject();
				source_info = temp;
				filereader.close();
			}
			else {
				if(!report_info.createNewFile()) {
					JOptionPane.showMessageDialog(frame, "Can't create your data file", "Exception", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else {
					ObjectOutputStream filewriter = new ObjectOutputStream(new FileOutputStream(report_info));
					String txt = savings.toString() + "," + savings.toString() + "," + t_liability.toString() + "," + perm_expense + "\n";
					filewriter.writeObject(txt);
					filewriter.writeObject(source_info);
					filewriter.close();
				}
			}
		} catch (IOException e2) {
			//Can't create your information space
			JOptionPane.showMessageDialog(null, "IOException" + e2.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
			e2.printStackTrace();
			return;
		} catch (ClassNotFoundException e3) {
			JOptionPane.showMessageDialog(null, "Class not Found Exception", "Exception", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//set default close operation
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowClosing(WindowEvent e) {
				int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit this Application?", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (confirm == JOptionPane.YES_OPTION) {
					ArrayList<String> lines = new ArrayList<String>();
					int size = 0;
					
					//take information
					try {
						BufferedReader reader = new BufferedReader(new FileReader(filename));
						String txt;
						while((txt = reader.readLine()) != null) {
							lines.add(txt);
							size += 1;
						}
						reader.close();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "Can't extract your information", "Exception", JOptionPane.ERROR_MESSAGE);
					}
					//store info
					String[] line = lines.get(file_index).split(",");
					String username = line[0];
					String password = line[1];
					
					//update info
					lines.set(file_index, username + "," + password + "," + t_money.toString() + "," + t_expense.toString() + "," + savings.toString() + "," + t_liability.toString() + "," + perm_expense.toString() + "," + month_pay.toString() + "\n");
					//to avoid leakage
					password = "";
					
					try {
						//write updated info in file
						BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
						int ind = 0;
						while(ind < size) {
							writer.write(lines.get(ind) + "\n");
							ind+=1;
						}
						
						writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Can't update information", "Exception", JOptionPane.ERROR_MESSAGE);
					}
					
					
					//extract user info from his file
					try {
						ObjectInputStream reader = new ObjectInputStream(new FileInputStream(report_info));
						String txt = "";
						//filewriter.write(savings.toString() + "," + savings.toString() + "," + t_liability.toString() + "," + perm_expense + "\n");
						if((txt= (String)reader.readObject()) == null) {
							JOptionPane.showMessageDialog(null, "Can't find your information", "Exception", JOptionPane.ERROR_MESSAGE);
							reader.close();
							return;
						}
						reader.close();
						
						//save new information in file
						ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(report_info));
						writer.writeObject(txt);
						//write source and expense information
						writer.writeObject(source_info);
						writer.close();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Can't find your information", "Exception", JOptionPane.ERROR_MESSAGE);
					} catch (IOException e2) {
						JOptionPane.showMessageDialog(null, "Can't find your information", "Exception", JOptionPane.ERROR_MESSAGE);
					} catch (ClassNotFoundException e2) {
						JOptionPane.showMessageDialog(null, "Can't find your information", "Exception", JOptionPane.ERROR_MESSAGE);
					}
					//close application
					frame.dispose();
					
					
				}
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	public void Open() {
		//create menubar
//		JMenuBar title_bar = new JMenuBar();
//		title_bar.setForeground(Color.LIGHT_GRAY);
//		JMenu backward_butt = new JMenu();
//		title_bar.add(backward_butt);
//		frame.add(title_bar);
		
		//make components
		JPanel title_bar = new JPanel();
		title_bar.setLayout(new BoxLayout(title_bar, BoxLayout.X_AXIS));
		title = new JLabel("Finance Manager");
		title.setFont(new Font("Aerial", Font.BOLD, 20));
		title_bar.add(Box.createRigidArea(new Dimension(180, 0)));
		title_bar.add(title);
		//time label
		JLabel datelabel = new JLabel("Time");
		datelabel.setForeground(Color.gray);
		title_bar.add(Box.createRigidArea(new Dimension(120, 0)));
		title_bar.add(datelabel);
		frame.add(title_bar);
		
		Timer date_tracker = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			datelabel.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
			}
		});
		date_tracker.setInitialDelay(0);
		date_tracker.start();
		
		//create recycle button
		JPanel rec_panel = new JPanel();
		rec_panel.setLayout(new BoxLayout(rec_panel, BoxLayout.X_AXIS));
		JButton recycle = new JButton("End Cycle");
		recycle.setPreferredSize(new Dimension(100, 30));
		recycle.setMaximumSize(new Dimension(100, 30));
		rec_panel.setPreferredSize(new Dimension(600, 40));
		rec_panel.setMaximumSize(new Dimension(600, 40));
		rec_panel.add(Box.createRigidArea(new Dimension(420, 0)));
		rec_panel.add(recycle);
		
		frame.add(Box.createRigidArea(new Dimension(0, 30)));
		frame.add(rec_panel);
		
		//make listener
		recycle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Initialize expense and transfer money from pocket to saving
				savings = t_money;
				t_expense = 0f;
				t_money = 0f;
				t_money += month_pay;
				t_expense += perm_expense;
				
				TMoney_value.setText(t_money.toString());
				TExpense_value.setText(t_expense.toString());
				TSaving_value.setText(savings.toString());
				
				try {
					//save information for report information
					//get info
					ObjectInputStream reader1 = new ObjectInputStream(new FileInputStream(report_info));
					String txt_temp = (String)reader1.readObject();
					HashMap<String, Float> temp = (HashMap<String, Float>)reader1.readObject();
					reader1.close();
					String[] txt_r = txt_temp.split(",");
					
					//update and save info
					ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(report_info));
					txt_temp = savings.toString() + "," + ((Float)((Float.parseFloat(txt_r[1]) + savings) / 2)).toString() + "," + t_liability.toString();
					writer.writeObject(txt_temp);
					writer.writeObject(temp);
					writer.close();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(frame, "Can't update your information", "Exception", JOptionPane.ERROR_MESSAGE);
				} catch(ClassNotFoundException e2) {
					JOptionPane.showMessageDialog(frame, "Can't update your information", "Exception", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JPanel summary = new JPanel();
		summary.setLayout(new BoxLayout(summary, BoxLayout.X_AXIS));
		
		//set size of boxes
		Dimension value_box_size = new Dimension(140, 100);
		
		JPanel money_box = new JPanel();
		money_box.setLayout(new BoxLayout(money_box, BoxLayout.Y_AXIS));
		//make label
		JLabel TMoney_label = new JLabel("Money in Pocket");
		TMoney_label.setAlignmentX(CENTER_ALIGNMENT);
		TMoney_label.setForeground(Color.green);
		money_box.add(TMoney_label);
		money_box.add(Box.createRigidArea(new Dimension(0, 30)));
		money_box.add(TMoney_value);
		money_box.setBackground(Color.black);
		money_box.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		money_box.setPreferredSize(value_box_size);
		money_box.setMaximumSize(value_box_size);
		
		JPanel ExpenseBox = new JPanel();
		ExpenseBox.setLayout(new BoxLayout(ExpenseBox, BoxLayout.Y_AXIS));
		JLabel TExpense_label = new JLabel("Monthly Expense");
		TExpense_label.setForeground(Color.red);
		TExpense_label.setAlignmentX(CENTER_ALIGNMENT);
		ExpenseBox.add(TExpense_label);
		ExpenseBox.add(Box.createRigidArea(new Dimension(0, 30)));
		ExpenseBox.add(TExpense_value);
		ExpenseBox.setBackground(Color.black);
		ExpenseBox.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		ExpenseBox.setPreferredSize(value_box_size);
		ExpenseBox.setMaximumSize(value_box_size);
		
		JPanel SavingBox = new JPanel();
		SavingBox.setLayout(new BoxLayout(SavingBox, BoxLayout.Y_AXIS));
		JLabel TSaving_label = new JLabel("Your Savings");
		TSaving_label.setForeground(Color.yellow);
		TSaving_label.setAlignmentX(CENTER_ALIGNMENT);
		SavingBox.add(TSaving_label);
		SavingBox.add(Box.createRigidArea(new Dimension(0, 30)));
		SavingBox.add(TSaving_value);
		SavingBox.setBackground(Color.black);
		SavingBox.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		SavingBox.setPreferredSize(new Dimension(120, 100));
		SavingBox.setMaximumSize(new Dimension(120, 100));
		
		JPanel LiabilityBox = new JPanel();
		LiabilityBox.setLayout(new BoxLayout(LiabilityBox, BoxLayout.Y_AXIS));
		JLabel TLiability_label = new JLabel("Liabilities");
		TLiability_label.setForeground(Color.ORANGE);
		TLiability_label.setAlignmentX(CENTER_ALIGNMENT);
		LiabilityBox.add(TLiability_label);
		LiabilityBox.add(Box.createRigidArea(new Dimension(0, 30)));
		LiabilityBox.add(TLiability_value);
		LiabilityBox.setBackground(Color.black);
		LiabilityBox.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		LiabilityBox.setPreferredSize(new Dimension(120, 100));
		LiabilityBox.setMaximumSize(new Dimension(120, 100));
		
		value = new JTextField();
		value.setHorizontalAlignment(JTextField.CENTER);
		value.setPreferredSize(new Dimension(200, 35));
		value.setMaximumSize(new Dimension(200, 35));
		value.setFont(new Font("Aerial", Font.PLAIN, 16));
		
		JPanel summ_add_butt = new JPanel();
		summ_add_butt.setLayout(new BoxLayout(summ_add_butt, BoxLayout.X_AXIS));
		//create addition button
		JButton money_addition = new JButton("Put Money in Pocket");
		money_addition.setPreferredSize(new Dimension(150, 40));
		money_addition.setMaximumSize(new Dimension(150, 40));
		money_addition.setBackground(Color.green);
		summ_add_butt.add(money_addition);
		summ_add_butt.add(Box.createRigidArea(new Dimension(50, 0)));
		//create expense addition button
		JButton expense_addition = new JButton("Add Expense");
		expense_addition.setPreferredSize(new Dimension(130, 40));
		expense_addition.setMaximumSize(new Dimension(130, 40));
		expense_addition.setBackground(Color.red);
		summ_add_butt.add(expense_addition);
		//create liability addition button
		JButton liability_addition = new JButton("Add Liability");
		liability_addition.setPreferredSize(new Dimension(130, 40));
		liability_addition.setMaximumSize(new Dimension(130, 40));
		liability_addition.setBackground(Color.orange);
		summ_add_butt.add(Box.createRigidArea(new Dimension(50, 0)));
		summ_add_butt.add(liability_addition);
		
		//create permanent expense addition button
		JPanel butt_row2 = new JPanel();
		butt_row2.setLayout(new BoxLayout(butt_row2, BoxLayout.X_AXIS));
		JButton perm_exp_addition = new JButton("Add Permanent Expense");
		perm_exp_addition.setPreferredSize(new Dimension(200, 40));
		perm_exp_addition.setMaximumSize(new Dimension(200, 40));
		perm_exp_addition.setBackground(Color.gray);
		butt_row2.add(perm_exp_addition);
		
		JButton month_pay_addition = new JButton("Set monthly Pay");
		month_pay_addition.setPreferredSize(new Dimension(200, 40));
		month_pay_addition.setMaximumSize(new Dimension(200, 40));
		month_pay_addition.setBackground(Color.CYAN);
		butt_row2.add(Box.createRigidArea(new Dimension(50, 0)));
		butt_row2.add(month_pay_addition);
		
		//make butt listeners for two buttons
		ActionListener money_add_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String txt = value.getText();
				
				//check if given value is number
				try {
					Float.parseFloat(txt);
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(frame, "Please enter number inside input field", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(!txt.equals("")) {
					float num = Float.valueOf(txt);
					
					//display Dialog
					frame.setEnabled(false);
					JDialog option = new JDialog();
					option.setLayout(new BoxLayout(option.getContentPane(), BoxLayout.Y_AXIS));
					option.setTitle("Success Message");
					
					JPanel mess_panel = new JPanel();
					mess_panel.setLayout(new BoxLayout(mess_panel, BoxLayout.X_AXIS));
					JLabel mess = new JLabel("Successfully added money to you pocket");
					mess_panel.add(mess);
					
					option.add(mess_panel);
					option.add(Box.createRigidArea(new Dimension(0, 50)));
					Dimension butt_size = new Dimension(100, 30);
					
					JPanel butts = new JPanel();
					butts.setLayout(new BoxLayout(butts, BoxLayout.X_AXIS));
					JButton ok = new JButton("OK");
					ok.setPreferredSize(new Dimension(butt_size));
					ok.setMaximumSize(butt_size);
					JButton cancel = new JButton("Cancel");
					cancel.setPreferredSize(new Dimension(butt_size));
					cancel.setMaximumSize(butt_size);
					butts.add(ok);
					butts.add(Box.createRigidArea(new Dimension(50, 0)));
					butts.add(cancel);
					butts.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
					option.add(butts);
					
					option.setSize(new Dimension(400, 150));
					option.setLocationRelativeTo(frame);
					option.setLocation(50, 300);
					
					ok.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							t_money += num;
							TMoney_value.setText(t_money.toString());
							value.setText("");
							frame.setEnabled(true);
							option.dispose();
						}
					});
					cancel.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							value.setText("");
							frame.setEnabled(true);
							option.dispose();
						}
					});
					option.setVisible(true);
				}
			}
		};
		
		ActionListener exp_add_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String txt = value.getText();
				if(!txt.equals("")) {
					float num = Float.parseFloat(txt);
					
					//display JDialog
					frame.setEnabled(false);
					JDialog option = new JDialog();
					option.setLayout(new BoxLayout(option.getContentPane(), BoxLayout.Y_AXIS));
					option.setTitle("Success Message");
					
					JPanel mess_panel = new JPanel();
					mess_panel.setLayout(new BoxLayout(mess_panel, BoxLayout.X_AXIS));
					JLabel mess = new JLabel("Successfully deducted money from your pocket");
					mess_panel.add(mess);
					
					option.add(mess_panel);
					option.add(Box.createRigidArea(new Dimension(0, 50)));
					Dimension butt_size = new Dimension(100, 30);
					
					JPanel butts = new JPanel();
					butts.setLayout(new BoxLayout(butts, BoxLayout.X_AXIS));
					JButton ok = new JButton("OK");
					ok.setPreferredSize(new Dimension(butt_size));
					ok.setMaximumSize(butt_size);
					JButton cancel = new JButton("Cancel");
					cancel.setPreferredSize(new Dimension(butt_size));
					cancel.setMaximumSize(butt_size);
					butts.add(ok);
					butts.add(Box.createRigidArea(new Dimension(50, 0)));
					butts.add(cancel);
					butts.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
					option.add(butts);
					
					option.setSize(new Dimension(400, 150));
					option.setLocationRelativeTo(frame);
					option.setLocation(50, 300);
					
					ok.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(num <= t_money) {
								//deduct money for pocket
								t_money -= num;
								//add expense value
								t_expense += num;
								//empty the text field
								value.setText("");
							}
							else {
								//find extra expense
								float extra_exp = num - t_money;
								//deduct available money from pocket
								t_money -= (num - extra_exp);
								//check for money in savings
								if(savings >= extra_exp) {
									//deduct extra amount from savings
									savings -= extra_exp;
									//add expense value
									t_expense += num;
									//empty the text field
									value.setText("");
								}
								else {
									//else show error of no balance
									JOptionPane.showMessageDialog(null, "You have insufficient balance for this expense", "Insufficient Balance", JOptionPane.ERROR_MESSAGE);
								}
							}
							TMoney_value.setText(t_money.toString());
							TExpense_value.setText(t_expense.toString());
							TSaving_value.setText(savings.toString());
							frame.setEnabled(true);
							option.dispose();
						}
					});
					cancel.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							value.setText("");
							frame.setEnabled(true);
							option.dispose();
						}
					});
					
					option.setVisible(true);
				}
			}
		};
		
		ActionListener liability_add_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String txt = value.getText();
				if(!txt.equals("")) {
					float num = Float.valueOf(txt);
					
					//display Dialog
					frame.setEnabled(false);
					JDialog option = new JDialog();
					option.setLayout(new BoxLayout(option.getContentPane(), BoxLayout.Y_AXIS));
					option.setTitle("Confirm Message");
					
					JPanel mess_panel = new JPanel();
					mess_panel.setLayout(new BoxLayout(mess_panel, BoxLayout.X_AXIS));
					JLabel mess = new JLabel("Are you Sure you want to add Liability?");
					mess_panel.add(mess);
					
					option.add(mess_panel);
					option.add(Box.createRigidArea(new Dimension(0, 50)));
					
					Dimension butt_size = new Dimension(100, 30);
					
					JPanel butts = new JPanel();
					butts.setLayout(new BoxLayout(butts, BoxLayout.X_AXIS));
					JButton add = new JButton("Add");
					add.setPreferredSize(new Dimension(butt_size));
					add.setMaximumSize(butt_size);
					JButton cancel = new JButton("Cancel");
					cancel.setPreferredSize(new Dimension(butt_size));
					cancel.setMaximumSize(butt_size);
					butts.add(add);
					butts.add(Box.createRigidArea(new Dimension(50, 0)));
					butts.add(cancel);
					butts.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
					option.add(butts);
					
					option.setSize(new Dimension(400, 150));
					option.setLocationRelativeTo(frame);
					option.setLocation(50, 300);
					
					add.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							t_liability += num;
							t_money += num;
							TLiability_value.setText(t_liability.toString());
							TMoney_value.setText(t_money.toString());
							value.setText("");
							frame.setEnabled(true);
							option.dispose();
						}
					});
					cancel.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							value.setText("");
							frame.setEnabled(true);
							option.dispose();
						}
					});
					option.setVisible(true);
				}
			}
		};
		
		ActionListener add_perm_expense = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//make dialog box
				JDialog info_taker = new JDialog();
				info_taker.getContentPane().setLayout(new BoxLayout(info_taker.getContentPane(), BoxLayout.Y_AXIS));
				info_taker.setTitle("Add permanent Expense");
				
				//make expense value text field
				JPanel value_panel = new JPanel();
				value_panel.setLayout(new BoxLayout(value_panel, BoxLayout.X_AXIS));
				JLabel value_txt = new JLabel("Expense Value: ");
				JTextField exp_value = new JTextField();
				exp_value.setPreferredSize(new Dimension(200, 30));
				exp_value.setMaximumSize(new Dimension(200, 30));
				value_panel.add(value_txt);
				value_panel.add(Box.createRigidArea(new Dimension(30, 0)));
				value_panel.add(exp_value);
				
				//make expense source text field
				JPanel source_panel = new JPanel();
				source_panel.setLayout(new BoxLayout(source_panel, BoxLayout.X_AXIS));
				JLabel source_txt = new JLabel("Expense Source: ");
				JTextField exp_src = new JTextField();
				exp_src.setPreferredSize(new Dimension(200, 30));
				exp_src.setMaximumSize(new Dimension(200, 30));
				source_panel.add(source_txt);
				source_panel.add(Box.createRigidArea(new Dimension(30, 0)));
				source_panel.add(exp_src);
				
				//create buttons
				Dimension butt_size = new Dimension(100, 30);
				JPanel butts = new JPanel();
				butts.setLayout(new BoxLayout(butts, BoxLayout.X_AXIS));
				JButton confirm = new JButton("Confirm");
				confirm.setPreferredSize(new Dimension(butt_size));
				confirm.setMaximumSize(butt_size);
				JButton cancel = new JButton("Cancel");
				cancel.setPreferredSize(new Dimension(butt_size));
				cancel.setMaximumSize(butt_size);
				butts.add(confirm);
				butts.add(Box.createRigidArea(new Dimension(50, 0)));
				butts.add(cancel);
				butts.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
				
				info_taker.add(value_panel);
				info_taker.add(Box.createRigidArea(new Dimension(0, 50)));
				info_taker.add(source_panel);
				info_taker.add(Box.createRigidArea(new Dimension(0, 30)));
				info_taker.add(butts);
				
				info_taker.setSize(new Dimension(400, 300));
				info_taker.setLocationRelativeTo(frame);
				info_taker.setLocation(50, 250);
				
				confirm.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						source_info.put(exp_src.getText(), Float.parseFloat(exp_value.getText()));
						value.setText("");
						frame.setEnabled(true);
						info_taker.dispose();
					}
				});
				
				cancel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//close the dialog window
						frame.setEnabled(true);
						info_taker.dispose();
					}
				});
				
				frame.setEnabled(false);
				
				info_taker.setVisible(true);
			}
		};
		
		ActionListener add_month_pay = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.setEnabled(false);
				JDialog option = new JDialog();
				option.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				option.setLayout(new BoxLayout(option.getContentPane(), BoxLayout.Y_AXIS));
				option.setTitle("Confirm Message");
				
				JPanel mess_panel = new JPanel();
				mess_panel.setLayout(new BoxLayout(mess_panel, BoxLayout.X_AXIS));
				JLabel mess = new JLabel("Are you Sure you want to set Monthly Pay?");
				mess_panel.add(mess);
				
				option.add(mess_panel);
				option.add(Box.createRigidArea(new Dimension(0, 50)));
				
				Dimension butt_size = new Dimension(100, 30);
				
				JPanel butts = new JPanel();
				butts.setLayout(new BoxLayout(butts, BoxLayout.X_AXIS));
				JButton add = new JButton("Confirm");
				add.setPreferredSize(new Dimension(butt_size));
				add.setMaximumSize(butt_size);
				JButton cancel = new JButton("No");
				cancel.setPreferredSize(new Dimension(butt_size));
				cancel.setMaximumSize(butt_size);
				butts.add(add);
				butts.add(Box.createRigidArea(new Dimension(50, 0)));
				butts.add(cancel);
				butts.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
				option.add(butts);
				
				option.setSize(new Dimension(400, 150));
				option.setLocationRelativeTo(frame);
				option.setLocation(50, 300);
				
				add.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String txt = value.getText();
						if(txt != "") {
							try {
								month_pay = Float.parseFloat(txt);
							} catch (NumberFormatException e1) {
								JOptionPane.showMessageDialog(frame, "Invalid Values in Input field", "Error", JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
						value.setText("");
						frame.setEnabled(true);
						option.dispose();
					}
				});
				cancel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						value.setText("");
						frame.setEnabled(true);
						option.dispose();
					}
				});
				option.setVisible(true);
			}
		};
		
		//add listeners to buttons
		money_addition.addActionListener(money_add_listener);
		expense_addition.addActionListener(exp_add_listener);
		liability_addition.addActionListener(liability_add_listener);
		perm_exp_addition.addActionListener(add_perm_expense);
		month_pay_addition.addActionListener(add_month_pay);
		
		frame.add(Box.createRigidArea(new Dimension(0, 20)));
		summary.add(money_box);
		summary.add(Box.createRigidArea(new Dimension(10, 0)));
		summary.add(ExpenseBox);
		summary.add(Box.createRigidArea(new Dimension(10, 0)));
		summary.add(SavingBox);
		summary.add(Box.createRigidArea(new Dimension(10, 0)));
		summary.add(LiabilityBox);
		
		frame.add(summary);
		frame.add(Box.createRigidArea(new Dimension(0, 50)));
		frame.add(value);
		frame.add(Box.createRigidArea(new Dimension(0, 30)));
		frame.add(summ_add_butt);
		frame.add(Box.createRigidArea(new Dimension(0, 30)));
		frame.add(butt_row2);
	}
	
	public void setVisibility(boolean ans) {
		frame.setVisible(ans);
	}
}


public class Main extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LoginPage login_page = new LoginPage();
		login_page.setVisible(true);
	}

}

class PlaceholderTextField extends JTextField {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String placeholder;

    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;
    }
    
    public void setPlaceholder(String placeholder) {
    	this.placeholder = placeholder;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (getText().isEmpty() && !(FocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this)) {
            Graphics2D g2 = (Graphics2D) g. create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getDisabledTextColor());
            g2.drawString(placeholder, getInsets().left, g.getFontMetrics().getMaxAscent() + getInsets().top);
	        g2.dispose();
        }
    }
}

class buttonManager extends JComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JButton getButton(String txt, int width, int height) {
		JButton but = new JButton(txt);
		but.setSize(width, height);
		return but;
	}
}