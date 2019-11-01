import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/*
A gradebook where students, assignments, and exams can be entered. The commands are: q: quit, addS <name>: add student,
addA: add assignment, addE: add exam, grade <name>: grades all assignments and exams for student, gradeA <number>: grades assignment # for all students,
gradeE <number>: grades exam # for all students, display: displays all students and grades.
*/
public class HW1 {
	public static void main(String[] args) throws IOException {
		String cmd = "";
		Scanner stdIn = new Scanner(System.in);
		while(!cmd.equals("q")) {
			int e = 0;
			int a = 0;
			ArrayList<String> students = new ArrayList<String>();
			ArrayList<ArrayList<Integer>> exams = new ArrayList<ArrayList<Integer>>();
			ArrayList<ArrayList<Integer>> assignments = new ArrayList<ArrayList<Integer>>();
			File grades = new File("grades.txt");
			grades.createNewFile();
			Scanner fr = new Scanner(grades);
			if(fr.hasNext()) {
				String[] s = fr.nextLine().split(", ");
				e = Integer.parseInt(s[0]);
				a = Integer.parseInt(s[1]);
				while(fr.hasNext()) {
					s = fr.nextLine().split(", ");
					students.add(s[0]);
					ArrayList<Integer> el = new ArrayList<Integer>();
					for(int i = 0; i < e; ++i) el.add(0);
					ArrayList<Integer> al = new ArrayList<Integer>();
					for(int i = 0; i < a; ++i) al.add(0);
					exams.add(el);
					assignments.add(al);
					boolean as = false;
					int li = 0;
					for(int i = 1; i < s.length; ++i) {
						if(s[i].equals("|")) { as = true; li = i; }
						else {
							if(!as) exams.get(students.size()-1).set(i-1, Integer.parseInt(s[i]));
							else assignments.get(students.size()-1).set(i-1-li, Integer.parseInt(s[i]));
						}
					}
				}
			}
			fr.close();
			System.out.println("Enter a command: ");
			cmd = stdIn.nextLine();
			String c[] = cmd.split(" ");
			if(c[0].equals("addS")) {
				if(c.length <= 1) System.out.println("Please specify a student name.");
				else if(students.contains(c[1])) System.out.println("Student already exists.");
				else { 
					students.add(c[1]);
					ArrayList<Integer> el = new ArrayList<Integer>();
					for(int i = 0; i < e; ++i) el.add(0);
					ArrayList<Integer> al = new ArrayList<Integer>();
					for(int i = 0; i < a; ++i) al.add(0);
					exams.add(el);
					assignments.add(al);
				}
			}
			else if(c[0].equals("addA")) {
				for(int i = 0; i < assignments.size(); ++i) {
					assignments.get(i).add(0);
				}
				++a;
			}
			else if(c[0].equals("addE")) {
				for(int i = 0; i < exams.size(); ++i) {
					exams.get(i).add(0);
				}
				++e;
			}
			else if(c[0].equals("grade")) {
				if(c.length <= 1) System.out.println("Please specify a student name.");
				else if(!students.contains(c[1])) System.out.println("Student not found.");
				else {
					int s = students.indexOf(c[1]);
					for(int i = 0; i < a; ++i) {
						int g = -1;
						System.out.println("Current grade of assignment #" + i + " is: " + assignments.get(s).get(i));
						while(g < 0 || g > 100) {
							System.out.println("Please enter new grade for assignment between 0 and 100: ");
							String t = stdIn.next();
							try {
								g = Integer.parseInt(t);
							} catch(NumberFormatException err) {
								System.out.println("Please enter a valid integer.");
							}
						}
						assignments.get(s).set(i, g);
					}
					for(int j = 0; j < e; ++j) {
						int g = -1;
						System.out.println("Current grade of exam #" + j + " is: " + exams.get(s).get(j));
						while(g < 0 || g > 100) {
							System.out.println("Please enter new grade for exam between 0 and 100: ");
							String t = stdIn.next();
							try {
								g = Integer.parseInt(t);
							} catch(NumberFormatException err) {
								System.out.println("Please enter a valid integer.");
							}
						}
						exams.get(s).set(j, g);
					}
				}
			}
			else if(c[0].equals("gradeA")) {
				if(c.length <= 1) System.out.println("Please enter an assignment number.");
				else {
					int n = -1;
					try {
						n = Integer.parseInt(c[1]);
					} catch(NumberFormatException err) {
						System.out.println("Please enter a valid integer.");
					}
					if(n < 0 || n > a) System.out.println("Please enter the number of an existing assignment.");
					else {
						for(int i = 0; i < students.size(); ++i) {
							int g = -1;
							while(g < 0 || g > 100) {
								System.out.println("Please enter grade for " + students.get(i) + " between 0 and 100: ");
								String t = stdIn.next();
								try {
									g = Integer.parseInt(t);
								} catch(NumberFormatException err) {
									System.out.println("Please enter a valid integer.");
								}
							}
							assignments.get(i).set(n-1, g);
						}
					}
				}
			}
			else if(c[0].equals("gradeE")) {
				if(c.length <= 1) System.out.println("Please enter an exam number.");
				else {
					int n = -1;
					try {
						n = Integer.parseInt(c[1]);
					} catch(NumberFormatException err) {
						System.out.println("Please enter a valid integer.");
					}
					if(n < 0 || n > e) System.out.println("Please enter the number of an existing exam.");
					else {
						for(int i = 0; i < students.size(); ++i) {
							int g = -1;
							while(g < 0 || g > 100) {
								System.out.println("Please enter grade for " + students.get(i) + " between 0 and 100: ");
								String t = stdIn.next();
								try {
									g = Integer.parseInt(t);
								} catch(NumberFormatException err) {
									System.out.println("Please enter a valid integer.");
								}
							}
							exams.get(i).set(n-1, g);
						}
					}
				}
			}
			else if(c[0].equals("display")) {
				for(int i = 0; i < students.size(); ++i) {
					int as = 0;
					int ex = 0;
					System.out.print(students.get(i) + ", ");
					for(int j = 0; j < e; ++j) {
						int t = exams.get(i).get(j);
						ex = ex + t;
						System.out.print(t + ", ");
					}
					System.out.print("|, ");
					for(int j = 0; j < a; ++j) {
						int t = assignments.get(i).get(j);
						as = as + t;
						System.out.print(t + ", ");
					}
					int gr = 0;
					if(e > 0 && a > 0) gr = ((ex/e) / 2) + ((as/a) / 2);
					else if(a > 0) gr = (as/a) / 2;
					else if(e > 0) gr = (ex/e) / 2;
					System.out.println(": " + gr + "%");
				}
			}
			else if(!c[0].equals("q")) System.out.println("No such command.");
			PrintWriter pw = new PrintWriter("grades.txt");
			pw.println(e + ", " + a);
			for(int i = 0; i < students.size(); ++i) {
				pw.print(students.get(i));
				for(int j = 0; j < e; ++j) {
					pw.print(", " + exams.get(i).get(j));
				}
				pw.print(", |");
				for(int j = 0; j < a; ++j) {
					pw.print(", " + assignments.get(i).get(j));
				}
				pw.println();
			}
			pw.close();
		}
		stdIn.close();
	}
}
