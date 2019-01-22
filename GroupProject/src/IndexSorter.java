import java.util.Comparator;

public class IndexSorter implements Comparator<Student>
	{
		@Override
		public int compare(Student s1, Student s2) 
			{
				if(s1.getIndex() > s2.getIndex())
					return 1;
				else
					return -1;
			}
	}
