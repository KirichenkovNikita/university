#Checkstyle
Description of the coding convention - 
[checkstyle.xml](../checkstyle.xml)
---

1. ```
   <property name="charset" value="UTF-8"/>
   ```
    
   **File encoding must be UTF-8**

2. ```
   <module name="FileLength"/>
   ```

   **The class length must not exceed a certain number of characters. 
   The default is 2000**
   
3. ```
   <module name="LineLength">
        <property name="fileExtensions" value="java"/>
        <property name="max" value="120"/>
    </module>
   ```

   **For all java files, the line length must not exceed 120 characters**

4. ```
   <module name="DeclarationOrder"/>
   ```

   **Checks that the parts of a class, record, or interface 
   declaration appear in the order suggested by the Code Conventions 
   for the Java Programming Language.**
   
5. ```
   <module name="EmptyLineSeparator">
            <property name="tokens" value="
            CLASS_DEF, ENUM_DEF, INTERFACE_DEF, CTOR_DEF, STATIC_INIT, INSTANCE_INIT, VARIABLE_DEF, METHOD_DEF"/>
            <property name="allowNoEmptyLineBetweenFields" value="true"/>
            <property name="allowMultipleEmptyLines" value="false"/>
            <property name="allowMultipleEmptyLinesInsideClassMembers" value="false"/>
   </module>
   ```

   **Checks for empty line separators before package, all import declarations, 
   fields, constructors, methods, nested classes, static initializers and 
   instance initializers.** 
   
   - Property *allowNoEmptyLineBetweenFields* - 
     Allow no empty line between fields
   - Property *allowMultipleEmptyLines* - 
     Allow multiple empty lines between class members
   - Property *allowMultipleEmptyLinesInsideClassMembers* - 
     Allow multiple empty lines inside class members
   - Property *tokens* - tokens to check Type is 
     java.lang.String[]. Validation type is tokenSet
   
  
6. ```
   <module name="ConstantName"/>
   ```
   
   **Checks that constant names conform to a specified pattern. 
   A constant is a static and final field or an interface/annotation 
   field, except serialVersionUID and serialPersistentFields**
  
 
7. ```
   <module name="LocalVariableName"/> 
   ```
   
   **Checks that local, non-final variable names conform to a 
   specified pattern. A catch parameter is considered to be a local variable.**


8. ```
   <module name="MethodName"/>
   ```

   **Checks that method names conform to a specified pattern.**
   
   - Property *applyToPublic* - Controls whether to apply the check to 
     public member. Type is boolean. Default value is true.
   - Property *applyToProtected* - Controls whether to apply the check 
     to protected member. Type is boolean. Default value is true.
   - Property *applyToPackage* - Controls whether to apply the check to 
     package-private member. Type is boolean. Default value is true.
   - Property *applyToPrivate* - Controls whether to apply the check to 
     private member. Type is boolean. Default value is true.
   
9. ```
   <module name="EmptyBlock"/>  
   ```

   **Checks for empty blocks. This check does not validate sequential blocks.**

10. ```
    <module name="LeftCurly"/>
    ```

   **Checks for the placement of left curly braces ('{') for code blocks.**

11. ```
    <module name="NeedBraces"/>   
    ```

   **Checks for braces around code blocks.**

12. ```
    <module name="RightCurly"/>  
    ```

   **Checks the placement of right curly braces (''}) for code blocks. 
   This check supports if-else, try-catch-finally blocks, while-loops, 
   for-loops, method definitions, class definitions, constructor 
   definitions, instance, static initialization blocks, annotation 
   definitions and enum definitions.**

13. ```
    <module name="ParameterNumber">
            <property name="max" value="3" />
            <property name="tokens" value="METHOD_DEF" />
            <message key = "maxParam" value = "The number of method parameters cannot contain 3." />
    </module>  
    ```

    **Checks the number of parameters of a method or constructor.**

    - Property *max* - Specify the maximum number of parameters allowed.
    - Property *ignoreOverriddenMethods* - Ignore number of 
    parameters for methods with @Override annotation.

14. ```
    <module name="Indentation">
            <property name="lineWrappingIndentation" value="4"/>
    </module>  
    ```
   
    **Checks correct indentation of Java code.**

    The idea behind this is that while pretty printers are sometimes convenient 
    for bulk reformats of legacy code, they often either aren't configurable enough 
    or just can't anticipate how format should be done. Sometimes this is personal 
    preference, other times it is practical experience. In any case, this check 
    should just ensure that a minimal set of indentation rules is followed.
    
    - Property *lineWrappingIndentation* - Specify how far continuation 
      line should be indented when line-wrapping is present
      
15. ```
    <module name="ParameterName"/>
    ```
    
    **Checks that method parameter names conform to a specified pattern. 
    By using accessModifiers property it is possible to specify different 
    formats for methods at different visibility levels**

16. ```
    <module name="TypeName"/>
    ```

    **Checks that type names conform to a specified pattern.**

    - Property format - Specifies valid identifiers. Type is java.util.regex.Pattern. Default value is "^[A-Z][a-zA-Z0-9]*$".
    - Property applyToPublic - Controls whether to apply the check to public member. Type is boolean. Default value is true.
    - Property applyToProtected - Controls whether to apply the check to protected member. Type is boolean. Default value is true.
    - Property applyToPackage - Controls whether to apply the check to package-private member. Type is boolean. Default value is true.

17. ```
    <module name="EmptyForIteratorPad"/>
    ```

    **Checks the padding of an empty for iterator; 
    that is whether a white space is required at an empty 
    for iterator, or such white space is forbidden.**

18. ```
    <module name="GenericWhitespace"/>
    ```

    **Checks that the whitespace around the Generic tokens (angle brackets) 
    "<" and ">" are correct to the 
    typical convention. The convention is not configurable.**

19. ```
    <module name="MethodParamPad"/>
    ```

    **Checks the padding between the identifier of a method definition, constructor definition, method call, 
    or constructor invocation; and the left parenthesis of the parameter list.**

20. ```
    <module name="NoWhitespaceAfter"/>
    ```

    **Checks that there is no whitespace after a token. More specifically, it checks that it is not followed by whitespace, or (if 
    linebreaks are allowed) all characters on the line after are whitespace.**

21. ```
    <module name="NoWhitespaceBefore"/>
    ```

    **Checks that there is no whitespace before a token. More specifically, it checks that it is not preceded with whitespace, 
    or (if linebreaks are allowed) all characters on the line before are whitespace.**

22. ```
    <module name="OperatorWrap"/>
    ```

    **Checks the policy on how to wrap lines on operators.**

    - Property option - Specify policy on how to wrap lines. Type is 
      com.puppycrawl.tools.checkstyle.checks.whitespace.WrapOption. Default value is nl.
    - Property tokens - tokens to check Type is java.lang.String[].

23. ```
    <module name="ParenPad"/>
    ```

    **Checks the policy on the padding of parentheses; that is whether a space is required after 
    a left parenthesis and before a right parenthesis, or such spaces are forbidden.**

24. ```
    <module name="TypecastParenPad"/>
    ```

    **Checks the policy on the padding of parentheses for typecasts. That is, whether a space is required after 
    a left parenthesis and before a right parenthesis, or such spaces are forbidden.**

25. ```
    <module name="WhitespaceAfter"/>
    ```

    **Checks that a token is followed by whitespace, with the exception that it 
    does not check for whitespace after the semicolon of an empty for iterator. 
    Use Check EmptyForIteratorPad to validate empty for iterators.**

26. ```
    <module name="WhitespaceAround"/>
    ```

    **Checks that a token is surrounded by whitespace. Empty constructor, method, 
    class, enum, interface, loop bodies (blocks), lambdas of the form**

