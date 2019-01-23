import java.util.ArrayList;

public class Student 
	{
		private String name;
		private String place;
		private int index;
		private ArrayList<Student> friends;
		
		public Student(String name)
		{
			this.name = name;
			friends = new ArrayList<Student>();
		}

		public String getName() 
		{
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPlace() {
			return place;
		}

		public void setPlace(String place) {
			this.place = place;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public ArrayList<Student> getFriends()
			{
				return friends;
			}

		public void setFriends(ArrayList<Student> friends)
			{
				this.friends = friends;
			}
		
	}
		
