import java.util.Comparator;

public class NumSorter implements Comparator<Student>
	{

	@Override
	public int compare(Student st1, Student st2) 
		{
			int s1 = Integer.valueOf(st1.getPlace().substring(1));
			int s2 = Integer.valueOf(st2.getPlace().substring(1));
			if(s1 > s2)
				return 1;
			else if(s1 < s2)
				return -1;
			return 0;
		}
		
	}
