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
		static ArrayList<ArrayList<ArrayList<Student>>> prevGroups = new ArrayList<ArrayList<ArrayList<Student>>>();
		static int groupSize = 4;
		static int numOfGroups;
		static int remainder;
		
		public static void main(String[] args) throws IOException
			{	
				readNames();
				numOfGroups = students.size() / groupSize;
				remainder = students.size() % groupSize;
				assignPlaces();
				
				for(int i = 0; i < numOfGroups; i++)
				{
					groups.add(new ArrayList<Student>());
				}
				
				makeGroups();
				System.out.println("Pass 1:");
				printGroups();
				prevGroups.add(groups);
				for(int i = 0; i < numOfGroups; i++)
					{
						groups.get(i).clear();
					}
				sortIndex();
				for(Student s: students)
					{
						ArrayList<Student> sF = s.getFriends();
						for(int i = 0; i < sF.size(); i++)
							{
								if(s.equals(sF.get(i)))
									{
										sF.remove(i);
									}
							}
					}
				for(Student s: students.get(0).getFriends())
					{
						System.out.print(s.getName() + " ");
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
			for(ArrayList<Student> group: groups)
			{
				System.out.println("-Group " + (groups.indexOf(group) + 1) + ":");
				for(Student s: group)
				{
					System.out.println("--" + s.getName() + " " + s.getPlace() + " " + s.getIndex());
				}
			}
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
//			for(int i = 0; i < remainder; i++)
//			{
//				groups.get(i).add(students.get((numOfGroups * groupSize) + i));
//			}
		}
		public static void sortIndex()
		{
			for(int x = 0; x < 2; x++)
				{
					System.out.println("Pass " + (2 + x) + ":");
					for(int i = 0; i < (numOfGroups); i++)
						{
							for(int g = 0; g < groupSize; g++)
							{
								Student s;
								boolean valid = true;
								int counter = 0;
								do
									{
										s = betGet((i * groupSize) + g + ((groupSize + x) * (g + 1)) + counter);
										for(Student t: groups.get(i))
											{
												if(t.getFriends().contains(s))
													{
														valid = false;
													}
											}
										counter++;
									}
								while(!valid);
								groups.get(i).add(s);
							}
							for(Student s: groups.get(i))
								{
									s.getFriends().addAll(groups.get(i));
								}
						}
					printGroups();
					for(int i = 0; i < numOfGroups; i++)
						{
							groups.get(i).clear();
						}
					for(Student s: students)
						{
							tempStudents.add(s);
						}
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
		public static void randChoose()
		{
			
			for(ArrayList<ArrayList<Student>> groups: prevGroups)
				{
				}
		}
	}
