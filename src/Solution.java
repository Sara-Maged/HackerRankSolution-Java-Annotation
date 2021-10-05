import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface FamilyBudget {
	String userRole() default "GUEST";
    int budgetLimit() default 0;
}

class FamilyMember {
    
	// budgetLimit = Spend + Budget Left
	@FamilyBudget(userRole = "SENIOR", budgetLimit = 100)
	public void seniorMember(int budget, int moneySpend) {
		System.out.println("Senior Member");
		System.out.println("Spend: " + moneySpend);
		System.out.println("Budget Left: " + (budget - moneySpend));
	}

	@FamilyBudget(userRole = "JUNIOR", budgetLimit = 50)
	public void juniorUser(int budget, int moneySpend) {
		System.out.println("Junior Member");
		System.out.println("Spend: " + moneySpend);
		System.out.println("Budget Left: " + (budget - moneySpend));
	}
}
public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        // taking number of text cases from user - ex: 3
		int testCases = Integer.parseInt(in.nextLine());
		while (testCases > 0) {
            // taking input fom user - ex: SENIOR 75
			String role = in.next();
			int spend = in.nextInt();
			try {
				Class annotatedClass = FamilyMember.class;
                // getting the methods from the family member class
				Method[] methods = annotatedClass.getMethods(); 
				for (Method method : methods) {
                    /* isAnnotationPresent Returns true if the field includes an annotation of the provided class type */
					if (method.isAnnotationPresent(FamilyBudget.class)) {
                        /*getAnnotation returns Method objects’s annotation for the  specified type passed as parameter if such an annotation is present, else null */
						FamilyBudget family = method
								.getAnnotation(FamilyBudget.class);
                                
						String userRole = family.userRole();
						int budgetLimit = family.budgetLimit();
                        
						if (userRole.equals(role)) {
							if(budgetLimit >= spend){
								method.invoke(FamilyMember.class.newInstance(),
										budgetLimit, spend);
							}else{
								System.out.println("Budget Limit Over");
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			testCases--;
		}

	}

}
