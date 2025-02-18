package crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class DemoEmployee {

	Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		DemoEmployee obj = new DemoEmployee();
		Scanner sc = new Scanner(System.in);
		int ch;

		do {
			System.out.println("\"welcome to EMS\" enter ur choice:");
			System.out.println("1.Insert the record");
			System.out.println("2.Displaying all the record");
			System.out.println("3.update the record");
			System.out.println("4.delete the record");
			System.out.println("5.search by id");
			System.out.println("6.Exit");
			ch = sc.nextInt();

			switch (ch) {

			case 1:
				obj.insertData();
				break;
			case 2:
				obj.displayData();
				break;
			case 3:
				obj.updateData();
				break;
			case 4:
				obj.deleteData();
				break;
			case 6:
				System.out.println("thanks for visiting us \"Student management system\"");
				break;
			case 5:
				obj.searchbyid();
				break;
			default:
				break;

			}

		} while (ch != 6);// if the condition is false then it will exit loop
		sc.close();
	}

//jdbc connection method
	public Connection getConnection() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "root");
			return con;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	};

	// methods
	public void insertData() {
		Employee s = new Employee();
		System.out.println("enter Employee name:");
		String ename = scanner.next();
		s.setEmpname(ename);
		System.out.println("enter designation:");
		String sdesig = scanner.next();
		s.setDesignation(sdesig);
		System.out.println("enter review:");
		int srev = scanner.nextInt();
		s.setReview(srev);
		System.out.println("enter salary:");
		int ssal = scanner.nextInt();
		s.setSalary(ssal);

		try {
			Connection con = getConnection();
			PreparedStatement pst = con
					.prepareStatement("insert into employees(empname,designation,review,salary) values(?,?,?,?)");
			pst.setString(1, s.getEmpname());
			pst.setString(2, s.getDesignation());
			pst.setInt(3, s.getReview());
			pst.setInt(4, s.getSalary());

			int x = pst.executeUpdate();

			if (x == 1) {
				System.out.println("Record inserted sucessfully");
			} else {
				System.out.println("Record inserted unsucessfully");
			}
			pst.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

	public void displayData() {
		try {
			Connection con = getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from sms.employees");
			while (rs.next()) {
				System.out.println(rs.getString(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + "  "
						+ rs.getString(4) + "  " + rs.getInt(5));
			}
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

	public void deleteData() {
		Employee s = new Employee();
		System.out.println("enter employeeid to delete the record:");
		int id = scanner.nextInt();
		s.setEmpid(id);

		try {

			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("delete from students where studentid=?");
			pst.setInt(1, s.getEmpid());
			int x = pst.executeUpdate();
			pst.close();
			con.close();
			if (x == 1) {
				System.out.println("record with empid:" + s.getEmpid() + " is deleted sucessfully");
			} else {
				System.out.println("record not found");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	};

	private void searchbyid() {

		Employee s = new Employee();
		System.out.println("enter employeeid to search the record:");
		int id = scanner.nextInt();
		s.setEmpid(id);

		try {

			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement("select * from employees where studentid=?");
			pst.setInt(1, s.getEmpid());
			ResultSet rs = pst.executeQuery();
//			pst.close();
//			con.close();
//			if (x == 1) {
//				System.out.println("record with empid:" + s.getEmpid() + " is deleted sucessfully");
//			} else {
//				System.out.println("record not found");
//			
//			}
//			Statement st = con.createStatement();
//			ResultSet rs = st.executeQuery("select * from students where studentid=?");
			while (rs.next()) {
				System.out.println(rs.getString(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + "  "
						+ rs.getString(4) + "  " + rs.getInt(5));
			}
			rs.close();
			pst.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	};

	public void updateData() {
		Employee s = new Employee();
		System.out.println("enter empid to update:");
		int sid = scanner.nextInt();
		s.setEmpid(sid);
		System.out.println("enter emp name:");
		String sname = scanner.next();
		s.setEmpname(sname);
		System.out.println("enter designation:");
		String semail = scanner.next();
		s.setDesignation(semail);
		System.out.println("enter review:");
		int scourse = scanner.nextInt();
		s.setReview(scourse);
		System.out.println("enter sal:");
		int sprice = scanner.nextInt();
		s.setSalary(sprice);
		try {
			Connection con = getConnection();
			String str = "update employees set empname=?,designation=?,review=?,salary=? where empid=?";
			PreparedStatement pst = con.prepareStatement(str);
			pst.setString(1, s.getEmpname());
			pst.setString(2, s.getDesignation());
			pst.setInt(3, s.getReview());
			pst.setInt(4, s.getSalary());
			pst.setInt(5, s.getEmpid());

			int x = pst.executeUpdate();
			pst.close();
			con.close();
			if (x == 1) {
				System.out.println("record updated sucessfully");
			} else {
				System.out.println("record not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	};

}