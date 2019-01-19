import java.util.Comparator;

public class LetterSorter implements Comparator<Student>
	{

	@Override
	public int compare(Student st1, Student st2) 
		{
			return st1.getPlace().substring(0,1).compareTo(st2.getPlace().substring(0, 1));
		}
		
	}
