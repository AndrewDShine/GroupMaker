import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GroupRunner 
	{
		final static String [] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		static ArrayList<Student> students = new ArrayList<Student>();
		static ArrayList<Student> tempStudents = new ArrayList<Student>();
		static ArrayList<ArrayList<Student>> groups = new ArrayList<ArrayList<Student>>();
		static Scanner user_IntPut = new Scanner(System.in);
		static int groupSize;
		static int numOfGroups;
		static int remainder;
		static int passNum = 1;
		
		public static void main(String[] args) throws IOException, InterruptedException
			{	
				readNames();
				System.out.println("Hello, User! Would you like to enter the number of students per group, or the number of groups?");
				System.out.println("1) Students per Group");
				System.out.println("2) Number of Groups");
				int choice = user_IntPut.nextInt();
				switch (choice)
				{
					case 1:
						System.out.println("Please enter the number of students you'd like per group!");
						groupSize = user_IntPut.nextInt();
						numOfGroups = students.size() / groupSize;
						remainder = students.size() % groupSize;
						break;
					case 2:
						System.out.println("Please enter the number of groups you'd like!");
						numOfGroups = user_IntPut.nextInt();
						groupSize = students.size() / numOfGroups;
						remainder = students.size() % groupSize;
						break;
				}
				assignPlaces();	
				for(int i = 0; i < numOfGroups; i++)
				{
					groups.add(new ArrayList<Student>());
				}
				for(int i = 0; i < 3; i++)
					{
						Thread.sleep(500);
						for(int j = 0; j < i+1; j++)
							{
								System.out.print(".");
							}
						System.out.println();
					}
				Thread.sleep(500);
				makeGroups();
				printGroups();
				for(ArrayList<Student> group: groups)
					{
						for(Student s: group)
							{
								s.getFriends().addAll(group);
								s.getFriends().remove(s);
							}
					}
				sort();
				sort();
				for(Student s: students)
					{
						checkFriends(s);
					}
			}
		
		public static void readNames() throws IOException
		{
			Scanner namesReader = new Scanner(new File("StudentList.txt"));
			while(namesReader.hasNext())
			{
				students.add(new Student(namesReader.nextLine()));
			}
		}
		public static void assignPlaces()
		{
			for(int i = 0; i < (numOfGroups); i++)
			{
				for(int g = 0; g < groupSize; g++)
				{
					students.get((i * groupSize) + g).setPlace(alphabet[i] + (g+1));
				}
			}
			for(int i = 0; i < remainder; i++)
			{
				students.get((numOfGroups * groupSize) + i).setPlace(alphabet[i] + (groupSize + 1));
			}
			for(int i = 0; i < students.size(); i++)
			{
				students.get(i).setIndex(i);
			}
			for(Student s: students)
				{
					tempStudents.add(s);
				}
		}
		public static void printGroups()
		{
			System.out.println("Pass " + passNum + ":");
			for(ArrayList<Student> group: groups)
			{
				System.out.println("-Group " + (groups.indexOf(group) + 1) + ":");
				for(Student s: group)
				{
					System.out.println("--" + s.getName() + " " + s.getIndex());
				}
			}
			passNum++;
		}
		public static void makeGroups()
		{
			for(int i = 0; i < (numOfGroups); i++)
			{
				for(int g = 0; g < groupSize; g++)
				{
					groups.get(i).add(students.get((i * groupSize) + g));
				}
			}
			for(int i = 0; i < remainder; i++)
			{
				groups.get(i).add(students.get((numOfGroups * groupSize) + i));
			}
		}
		public static Student betGet(int index)
		{
			if(index >= tempStudents.size())
					return betGet(index - tempStudents.size());
			else
				{
					Student s = tempStudents.get(index);
					tempStudents.remove(s);
					return s;
				}
		}
		public static boolean checkFriends(Student s1)
		{
//			System.out.println("Checking " + s1.getName());
			boolean hasOverlap = false;
			for(Student s: s1.getFriends())
				{
					int counter = 0;
					for(Student t: s1.getFriends())
						{
							if(s.equals(t))
								counter++;
						}
					if(counter > 1)
						hasOverlap = true;
				}
			return hasOverlap;
		}
		public static void sequentialChoose()
		{
			tempStudents.clear();
			for(Student s: students)
				{
					tempStudents.add(s);
				}
			for(ArrayList<Student> group: groups)
				{
					group.clear();
				}
			for(int i = 0; i < numOfGroups; i++)
				{
					groups.get(i).add(betGet(0));
				}
			for(ArrayList<Student> group: groups)
				{
					for(int i = 0; i < groupSize - 1; i++)
						{
							for(int j = 0; j < tempStudents.size(); j++)
								{
									boolean valid = true;
									Student t = tempStudents.get(j);
									for(Student s: group)
										{
											if(s.getFriends().contains(t))
												{
													valid = false;
												}
										}
									if(valid)
										{
											group.add(betGet(j));
											break;
										}
								}
						}
				}
			if(tempStudents.size() > 0)
				{
					for(int i = 0; i < tempStudents.size(); i++)
						{
							Student t = tempStudents.get(i);
							for(ArrayList<Student> group: groups)
								{
									boolean valid = true;
									for(Student s: group)
										{
											if(s.getFriends().contains(t))
												{
													valid = false;
												}
										}
									if(valid)
										{
											group.add(betGet(i));
											break;
										}
								}
						}
				}
			printGroups();
			if(tempStudents.size() > 0)
				{
					System.out.println("These students couldn't be grouped:");
					for(Student s: tempStudents)
						{
							System.out.print(" " + s.getName() + ",");
						}
					System.out.println();
				}
		}
		public static void sort() throws InterruptedException
		{
			for(int i = 0; i < 3; i++)
				{
					Thread.sleep(500);
					for(int j = 0; j < i+1; j++)
						{
							System.out.print(".");
						}
					System.out.println();
				}
			Thread.sleep(500);
			sequentialChoose();
			for(ArrayList<Student> group: groups)
				{
					for(Student s: group)
						{
							s.getFriends().addAll(group);
							s.getFriends().remove(s);
						}
				}
		}
	}
