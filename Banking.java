package assignment1;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;



public class Banking {



Scanner s = new Scanner(System.in);

public void register() {


try{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee","root","Pass@123");
PreparedStatement pstmt = con.prepareStatement("insert into bank values(?,?,?,?,?,?,?)");

System.out.println("Enter Name: ");
String name = s.next();

System.out.println("Enter Account Number: ");
long accountNumber = s.nextLong();

System.out.println("Enter Phone Number: ");
long phoneNumber = s.nextLong();

System.out.println("Enter Balance: ");
long balance = s.nextLong();

System.out.println("Enter IFSC: ");
String ifsc = s.next();

System.out.println("Enter UserID: ");
String userid = s.next();

System.out.println("Enter Password: ");
String password = s.next();

pstmt.setString(1, name);
pstmt.setLong(2,accountNumber);
pstmt.setLong(3,phoneNumber);
pstmt.setLong(4,balance);
pstmt.setString(5, ifsc);
pstmt.setString(6, userid);
pstmt.setString(7, password);
pstmt.execute();

System.out.println("USER REGISTERED !");
}
catch(Exception e){
System.out.println(e);
}
}

public void transfer(String userid) {

try {
Class.forName("com.mysql.cj.jdbc.Driver");
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee","root","Pass@123");
PreparedStatement send = con.prepareStatement("update bank set balance = balance - (?) where userid=?");
PreparedStatement rec = con.prepareStatement("update bank set balance = balance + (?) where phoneNumber=(?) or accountNumber = ?");


System.out.println("Enter the Reciever's Phone number or Account Number: ");
long recInput = s.nextLong();
System.out.println("Enter the amount: ");
int amount = s.nextInt();


send.setInt(1,amount);
send.setString(2,userid);
rec.setInt(1,amount);
rec.setLong(2,recInput);
rec.setLong(3,recInput);
send.executeUpdate();
rec.executeUpdate();

System.out.println("Transfer Done!");

}
catch(Exception e) {
System.out.println(e);
}
}

public void checkBalance(String userid) {
try {
Class.forName("com.mysql.cj.jdbc.Driver");
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee","root","Pass@123");
PreparedStatement pstmt = con.prepareStatement("select * from bank where userid = ?");

pstmt.setString(1,userid);

ResultSet r = pstmt.executeQuery();

while(r.next()) {
System.out.println("Hello " + r.getString(1)+", your account balance is: "+r.getInt(4));
}

}
catch(Exception e) {
System.out.println(e);
}
}

public boolean login(String userid, String password) {
try {
Class.forName("com.mysql.cj.jdbc.Driver");
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee","root","Pass@123");
PreparedStatement pstmt = con.prepareStatement("select * from bank");

//
// System.out.println("------LOGIN-----");
// System.out.println("Enter userid and password : ");
// String userid = s.next();
// String password = s.next();
ResultSet rs = pstmt.executeQuery();
while(rs.next())
{
String a = rs.getString(6);
String x = rs.getString(7);
if(a.equals(userid) && x.equals(password))
return true;

}

}
catch(Exception e){
System.out.println(e);
}

return false;

}

public static void main(String[] args) {
Scanner s = new Scanner(System.in);
Banking b = new Banking();
int ch1 = 0;
int ch2 = 0;
do {



System.out.println("------WELCOME TO ZEE BANK------\n");
System.out.println("Enter a choice:\n1. Login\n2. Register\n3. Exit");
ch1 = s.nextInt();
//switch(ch1) {
//case 1:
if(ch1==1){
System.out.println("Enter the UserID : ");
String userid = s.next();
System.out.println("Enter the Password : ");
String password = s.next();
if(b.login(userid, password))
{
do {
System.out.println("Enter a choice:\n1. Check Balance\n2. Transfer\n3. Logout");
ch2 = s.nextInt();
if(ch2==1)
b.checkBalance(userid);
else if(ch2==2)
b.transfer(userid);



}while(ch2<3);
}
else {
System.out.println("Wrong Credentials!\n");
}

}
else if(ch1 == 2)
b.register();

}while(ch1<3);

}
}

