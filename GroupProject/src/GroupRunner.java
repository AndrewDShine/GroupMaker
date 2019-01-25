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
		static int groupSize = 4;
		static int numOfGroups;
		static int remainder;
		static int passNum = 0;
		
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
				for(ArrayList<Student> group: groups)
					{
						for(Student s: group)
							{
								s.getFriends().addAll(group);
								s.getFriends().remove(s);
							}
					}
				System.out.println("Pass " + passNum + ":");
				printGroups();
				sortIndex();
				sortIndex();
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
			for(ArrayList<Student> group: groups)
			{
				System.out.println("-Group " + (groups.indexOf(group) + 1) + ":");
				for(Student s: group)
				{
					System.out.println("--" + s.getName() + " " + s.getIndex());
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
			for(int i = 0; i < remainder; i++)
			{
				groups.get(i).add(students.get((numOfGroups * groupSize) + i));
			}
			passNum++;
		}
		public static void sortIndex()
		{
			tempStudents.clear();
			for(Student s: students)
				{
					tempStudents.add(s);
				}
			for(ArrayList<Student> group: groups)
				{
					int startIndex = groups.get(0).get(0).getIndex();
					startIndex += groups.indexOf(group);
					group.clear();
					group.add(betGet(startIndex));
					for(int i = 0; i < groupSize - 1; i++)
						{
							for(Student s: group)
								{
									if(s.getFriends().contains(students.get(startIndex + ((3 + passNum) * (i + 1)))))
										System.out.println("fails");;
								}
							group.add(betGet(startIndex + ((3 + passNum) * (i + 1))));
						}
				}
			for(Student s: tempStudents)
				{
					for(ArrayList<Student> group: groups)
						{
							boolean valid = true;
							for(Student t: group)
								{
									if(s.getFriends().contains(t))
										{
											valid = false;
										}
								}
							if(valid)
								group.add(s);
							break;
						}
				}
			for(ArrayList<Student> group: groups)
				{
					for(Student s: group)
						{
							s.getFriends().addAll(group);
							s.getFriends().remove(s);
						}
				}
			passNum++;
			System.out.println("Pass " + passNum + ":");
			printGroups();
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
			System.out.println("rand");
			boolean validGroups = true;
			for(int i = 0; i < 100; i++)
				{
					Collections.shuffle(students);
					makeGroups();
					for(ArrayList<Student> group: groups)
						{
							for(Student s: group)
								{
									for(Student t: group)
										{
											if(s.getFriends().contains(t))
												{
													validGroups = false;
												}
										}
								}
						}
					if(validGroups)
						{
							break;
						}
				}
			if(!validGroups)
				{
					System.out.println("No more unique groups can be created.");
				}
			else
				{
					System.out.println("Rand Pass:");
					printGroups();
					for(int i = 0; i < numOfGroups; i++)
						{
							groups.get(i).clear();
						}
				}
			
		}
		public static void checkFriends(Student s1)
		{
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
			if(hasOverlap)
				System.out.println(s1.getName() + " has overlap.");
		}
	}
