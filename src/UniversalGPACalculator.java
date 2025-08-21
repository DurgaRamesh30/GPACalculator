import java.util.*;

public class UniversalGPACalculator {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        double scale = getValidScale(); // ✅ Ask until valid GPA scale
        int n = getValidInt("Enter number of subjects: ", 1, 50); // ✅ Number of subjects

        double totalPoints = 0, totalCredits = 0;

        for (int i = 1; i <= n; i++) {
            System.out.println("\n--- Subject " + i + " ---");

            String grade = getValidGrade(scale); // ✅ Ask until valid grade
            int credit = getValidInt("Enter credit hours for subject " + i + ": ", 1, 10); // ✅ Credit hours
            boolean isHonors = getYesNo("Is this an Honors/AP course? (yes/no): "); // ✅ yes/no only

            double gradePoint = convertGradeToPoints(grade, scale);

            if (isHonors) {
                gradePoint += 0.5;
                // Cap GPA at max scale
                gradePoint = Math.min(gradePoint, scale);
            }

            totalPoints += gradePoint * credit;
            totalCredits += credit;
        }

        double gpa = totalPoints / totalCredits;
        System.out.printf("\n✅ Your GPA on %.1f scale is: %.2f\n", scale, gpa);
    }

    // ✅ Get GPA scale safely
    private static double getValidScale() {
        while (true) {
            System.out.print("Choose GPA scale (4.0 / 5.0 / 10.0 / %): ");
            String input = sc.next();
            if (input.equals("4.0")) return 4.0;
            if (input.equals("5.0")) return 5.0;
            if (input.equals("10.0")) return 10.0;
            if (input.equals("%") || input.equals("100.0")) return 100.0;
            System.out.println("❌ Invalid scale! Please choose 4.0, 5.0, 10.0, or %.");
        }
    }

    // ✅ Get integer safely (within range)
    private static int getValidInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(sc.next());
                if (val >= min && val <= max) return val;
                System.out.println("❌ Value must be between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid number. Try again.");
            }
        }
    }

    // ✅ Get yes/no safely
    private static boolean getYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.next().toLowerCase();
            if (input.equals("yes")) return true;
            if (input.equals("no")) return false;
            System.out.println("❌ Please answer 'yes' or 'no'.");
        }
    }

    // ✅ Get grade safely depending on scale
    private static String getValidGrade(double scale) {
        while (true) {
            System.out.print("Enter grade/marks: ");
            String grade = sc.next();

            try {
                convertGradeToPoints(grade, scale); // check validity
                return grade;
            } catch (IllegalArgumentException e) {
                System.out.println("❌ " + e.getMessage() + " Try again.");
            }
        }
    }

    // ✅ Conversion logic with validation
    static double convertGradeToPoints(String grade, double scale) {
        grade = grade.toUpperCase();

        if (scale == 4.0) {
            switch (grade) {
                case "A+": case "A": return 4.0;
                case "A-": return 3.7;
                case "B+": return 3.3;
                case "B": return 3.0;
                case "B-": return 2.7;
                case "C+": return 2.3;
                case "C": return 2.0;
                case "D": return 1.0;
                case "F": return 0.0;
                default: throw new IllegalArgumentException("Invalid grade for 4.0 scale.");
            }
        } else if (scale == 5.0) {
            switch (grade) {
                case "A": return 5.0;
                case "B": return 4.0;
                case "C": return 3.0;
                case "D": return 2.0;
                case "F": return 0.0;
                default: throw new IllegalArgumentException("Invalid grade for 5.0 scale.");
            }
        } else if (scale == 10.0) {
            try {
                int marks = Integer.parseInt(grade);
                if (marks < 0 || marks > 100) throw new IllegalArgumentException("Marks must be 0–100.");
                return marks / 10.0;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Enter numeric marks for 10.0 scale.");
            }
        } else if (scale == 100.0) {
            try {
                int marks = Integer.parseInt(grade);
                if (marks < 0 || marks > 100) throw new IllegalArgumentException("Marks must be 0–100.");
                return marks;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Enter numeric marks for % scale.");
            }
        }

        throw new IllegalArgumentException("Unsupported GPA scale.");
    }
}
