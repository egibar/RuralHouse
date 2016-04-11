package exceptions;
public class OverlappingUserExists extends Exception {
 private static final long serialVersionUID = 1L;
 
 public OverlappingUserExists()
  {
    super();
  }
  /**This exception is triggered if there exists an overlapping offer
  *@param String
  *@return None
  */
  public OverlappingUserExists(String s)
  {
    super(s);
  }
}