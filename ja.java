import java.util.ArrayList;
import java.util.List;
import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.*;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.*;


public class ja {

  static String getMainClassName(String code) {
    final String s = "public\\s+class\\s+(?<name>\\w+)";
    final Pattern pat = Pattern.compile(s);
    Matcher mat = pat.matcher(code);
    if (mat.find()) {
      //System.out.print("Start index: " + mat.start());
      //System.out.print(" End index: " + mat.end() + " ");
      //System.out.println(mat.group("name"));
      return mat.group("name");
    } else {
      return "<Main class not found!>";
    }
  }

  static void eval(String code) {
    final InMemoryCompiler.IMCSourceCode cls1source;
    //final InMemoryCompiler.IMCSourceCode cls2source;

    final StringBuilder sb = new StringBuilder();
    //sb.append("package toast;\n");

    /*sb.append("class Test2 {\n");
    sb.append("    public Test2() {\n");
    sb.append("        System.out.println(\"class Test 222222222 constructor reporting in.\");\n");
    sb.append("        System.out.println(new DynaClass());\n");
    sb.append("    }\n");
    sb.append("}\n");

    sb.append("public class DynaClass {\n");
    sb.append("    public static void main(final String[] args) {");
    sb.append("        System.out.println(\"This is the main method speaking.\");\n");
    sb.append("        System.out.println(\"Args: \" + java.util.Arrays.toString(args));\n");
    sb.append("        final Test2 test = new Test2();\n");
    sb.append("    }\n");
    sb.append("    public String toString() {\n");
    sb.append("        return \"Hello, I am \" + ");
    sb.append("this.getClass().getSimpleName();\n");
    sb.append("    }\n");
    sb.append("}\n");
    final String code = sb.toString();*/

    /*Scanner sca = new Scanner(System.in).useDelimiter("\\A");
    String code = sca.hasNext() ? sca.next() : "";*/
    /*final String code;
    try {
      ByteArrayOutputStream result = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int length;
      while ((length = System.in.read(buffer)) != -1) {
        result.write(buffer, 0, length);
      }
      // StandardCharsets.UTF_8.name() > JDK 7
      code = result.toString("UTF-8");
    } catch (Exception e) {
      System.out.println(e);
      System.exit(1);
      return;
    }*/

    final String mainClassName = getMainClassName(code);

    cls1source = new InMemoryCompiler.IMCSourceCode(mainClassName, code);

    /*sb.setLength(0);
    sb.append("package toast;\n");
    sb.append("public class Test {\n");
    sb.append("    public Test() {\n");
    sb.append("        System.out.println(\"class Test constructor reporting in.\");\n");
    sb.append("        System.out.println(new DynaClass());\n");
    sb.append("    }\n");
    sb.append("}\n");
    cls2source = new InMemoryCompiler.IMCSourceCode("toast.Test", sb.toString());*/

    final List<InMemoryCompiler.IMCSourceCode> classSources = new ArrayList<>();
    classSources.add(cls1source);
    //classSources.add(cls2source);

    final InMemoryCompiler uCompiler = new InMemoryCompiler(classSources);
    final boolean ok = uCompiler.compile();
    //System.out.println("\n\nCOMPILER FEEDBACK: " + ok);

    if (ok) {
      try {
        //System.out.println("\nRUN:");
        //uCompiler.runToString(cls1source.fullClassName);
        uCompiler.runMain(cls1source.fullClassName, new String[] { "arg1", "arg2", "arg3" });
      } catch (Exception e) {
        e.printStackTrace();
      }
      /*try {
        System.out.println("\nMAIN DEMO:");
        uCompiler.runMain(cls1source.fullClassName, new String[] { "test1", "test2" });
      } catch (Exception e) {
        e.printStackTrace();
      }*/
    }
  }

  static String loadFile(String fn) {
    try {
      String content = Files.readString(
        Paths.get(fn),
        StandardCharsets.US_ASCII);
      return content;
    } catch (Exception e) {
      System.out.println(e);
    }
    return "";
  }

  public static void main(String[] args) {
    long t, t2;

    //System.out.println(Arrays.toString(args));
    if (args.length > 0 && args[0].equals("warmup")) {
      //System.out.println("warm up");
      //t = System.currentTimeMillis();
      eval(loadFile("warmup.java"));
      //t2 = System.currentTimeMillis();
    } else {
      //System.out.println("no warm up");
    }

    System.out.print("READY");
    System.out.flush();
    //System.out.println((t2 - t) + " ms");

    final String code;
    try {
      ByteArrayOutputStream result = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int length;
      while ((length = System.in.read(buffer)) != -1) {
        result.write(buffer, 0, length);
      }
      // StandardCharsets.UTF_8.name() > JDK 7
      code = result.toString("UTF-8");
      //t = System.currentTimeMillis();
      eval(code);
      //t2 = System.currentTimeMillis();
      //System.out.println((t2 - t) + " ms");
    } catch (Exception e) {
      System.out.println(e);
      System.exit(1);
      return;
    }



    /*if (args.length != 1) {
        System.err.println("usage: java JaSv.java <port number>");
        System.exit(1);
    }

    int portNumber = Integer.parseInt(args[0]);
    long t, t2;

    try (
        ServerSocket serverSocket = new ServerSocket(portNumber);
        Socket clientSocket = serverSocket.accept();
        PrintWriter out =
            new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
            new InputStreamReader(clientSocket.getInputStream()));
    ) {    */

    /*StringBuilder sb = new StringBuilder();
    sb.append("public class Test {\n");
    sb.append("    public static void main(String[] args) {\n");
    sb.append("        System.out.println(\"class Test 222222222 constructor reporting in.\");\n");
    sb.append("        //System.out.println(new DynaClass());\n");
    sb.append("    }\n");
    sb.append("}\n");
    String code = sb.toString();*/

    /*for (int i = 0; i < 2000; i++) {
      System.out.println(i);
      t = System.currentTimeMillis();
      eval(loadFile("1.java"));
      t2 = System.currentTimeMillis();
      System.out.println((t2 - t) + " ms");
      System.gc();
      System.gc();
      System.runFinalization ();
    }*/

    /*t = System.currentTimeMillis();
    eval(loadFile("2.java"));
    t2 = System.currentTimeMillis();
    System.out.println((t2 - t) + " ms");*/

    /*try {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
br.readLine();
    } catch (Exception e) {
      System.out.println(e);
    }*/
    //System.out.println("exit");
    System.exit(0);
  }
}




/*final class CompilerFeedback {

    final public boolean success;
    final public List<CompilerMessage> messages = new ArrayList<>();

    public CompilerFeedback(final Boolean success, final DiagnosticCollector<JavaFileObject> diagnostics) {

        this.success = success != null && success;
        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
            //messages.add(new CompilerMessage(diagnostic));
            System.out.println(diagnostic);
        }
    }

    public String toString() {

        final StringBuilder sb = new StringBuilder();

        sb.append("SUCCESS: ").append(success).append('\n');
        final int iTop = messages.size();
        for (int i = 0; i < iTop; i++) {
            sb.append("\n[MESSAGE ").append(i + 1).append(" OF ").append(iTop).append("]\n\n");
            // sb.append(messages.get(i).toString()).append("\n");
            // sb.append(messages.get(i).toStringForList()).append("\n");
            sb.append(messages.get(i).toStringForDebugging()).append("\n");
        }
        return sb.toString();
    }

    final public static class CompilerMessage {

        final public Diagnostic<? extends JavaFileObject> compilerInfo;

        final public String typeOfProblem;
        final public String typeOfProblem_forDebugging;

        final public String multiLineMessage;

        final public int lineNumber;
        final public int columnNumber;

        final public int textHighlightPos_lineStart;
        final public int textHighlightPos_problemStart;
        final public int textHighlightPos_problemEnd;

        final public String sourceCode;
        final public String codeOfConcern;
        final public String codeOfConcernLong;

        CompilerMessage(final Diagnostic<? extends JavaFileObject> diagnostic) {

            final JavaFileObject sourceFileObject = diagnostic.getSource();
            String sourceCodePreliminary = null;
            if (sourceFileObject instanceof SimpleJavaFileObject) {
                final SimpleJavaFileObject simpleSourceFileObject = (SimpleJavaFileObject) sourceFileObject;

                try {
                    final CharSequence charSequence = simpleSourceFileObject.getCharContent(false);
                    sourceCodePreliminary = charSequence.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sourceCodePreliminary == null) {
                sourceCode = "[SOURCE CODE UNAVAILABLE]";
            } else {
                sourceCode = sourceCodePreliminary;
            }

            compilerInfo = diagnostic;

            typeOfProblem = diagnostic.getKind().name();
            typeOfProblem_forDebugging = "toString() = " + diagnostic.getKind().toString() + "; name() = " + typeOfProblem;

            lineNumber = (int) compilerInfo.getLineNumber();
            columnNumber = (int) compilerInfo.getColumnNumber();

            final int sourceLen = sourceCode.length();
            textHighlightPos_lineStart = (int) Math.min(Math.max(0, diagnostic.getStartPosition()), sourceLen);
            textHighlightPos_problemStart = (int) Math.min(Math.max(0, diagnostic.getPosition()), sourceLen);
            textHighlightPos_problemEnd = (int) Math.min(Math.max(0, diagnostic.getEndPosition()), sourceLen);

            final StringBuilder reformattedMessage = new StringBuilder();
            final String message = diagnostic.getMessage(Locale.US);
            final int messageCutOffPosition = message.indexOf("location:");
            final String[] messageParts;
            if (messageCutOffPosition >= 0) {
                messageParts = message.substring(0, messageCutOffPosition).split("\n");
            } else {
                messageParts = message.split("\n");
            }
            for (String s : messageParts) {
                String s2 = s.trim();
                if (s2.length() > 0) {
                    boolean lengthChanged;
                    do {
                        final int lBeforeReplace = s2.length();
                        s2 = s2.replace("  ", " ");
                        lengthChanged = (s2.length() != lBeforeReplace);
                    } while (lengthChanged);
                    reformattedMessage.append(s2).append("\n");
                }
            }

            codeOfConcern = sourceCode.substring(textHighlightPos_problemStart, textHighlightPos_problemEnd);
            codeOfConcernLong = sourceCode.substring(textHighlightPos_lineStart, textHighlightPos_problemEnd);
            if (!codeOfConcern.isEmpty()) {
                reformattedMessage.append("Code of concern: \"").append(codeOfConcern).append('\"');
            }
            multiLineMessage = reformattedMessage.toString();
        }

        public String toStringForList() {

            if (compilerInfo == null) {
                return "No compiler!";
            } else {
                return compilerInfo.getCode();
            }
        }

        public String toStringForDebugging() {

            final StringBuilder ret = new StringBuilder();

            ret.append("Type of problem: ").append(typeOfProblem_forDebugging).append("\n\n");
            ret.append("Message:\n").append(multiLineMessage).append("\n\n");

            ret.append(compilerInfo.getCode()).append("\n\n");

            ret.append("line number: ").append(lineNumber).append("\n");
            ret.append("column number: ").append(columnNumber).append("\n");

            ret.append("textHighlightPos_lineStart: ").append(textHighlightPos_lineStart).append("\n");
            ret.append("textHighlightPos_problemStart: ").append(textHighlightPos_problemStart).append("\n");
            ret.append("textHighlightPos_problemEnd: ").append(textHighlightPos_problemEnd).append("\n");

            return ret.toString();
        }

        @Override
        public String toString() {

            //            return compilerInfo.getMessage(Locale.US);
            return typeOfProblem + ": " + multiLineMessage + "\n";
        }
    }
}*/

// MASSIVELY based on
// http://javapracs.blogspot.de/2011/06/dynamic-in-memory-compilation-using.html
// by Rekha Kumari (June 2011)
final class InMemoryCompiler {

  final public static class IMCSourceCode {
    final public String fullClassName;
    final public String sourceCode;

    /**
     * @param fullClassName Full name of the class that will be compiled. If the class should be in some package,
     *                      fullName should contain it too, for example: "testpackage.DynaClass"
     * @param sourceCode    the source code
     */
    public IMCSourceCode(final String fullClassName, final String sourceCode) {

        this.fullClassName = fullClassName;
        this.sourceCode = sourceCode;
    }
  }

  final public boolean valid;

  final private List<IMCSourceCode> classSourceCodes;
  final private JavaFileManager fileManager;

  public InMemoryCompiler(final List<IMCSourceCode> classSourceCodes) {

    this.classSourceCodes = classSourceCodes;

    final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    if (compiler == null) {
      fileManager = null;
      valid = false;
      System.err.println("ToolProvider.getSystemJavaCompiler() returned null! This program needs to be run on a system with an installed JDK.");
      return;
    }
    valid = true;

    fileManager = new ForwardingJavaFileManager<JavaFileManager>(compiler.getStandardFileManager(null, null, null)) {

      final private Map<String, ByteArrayOutputStream> byteStreams = new HashMap<>();

      @Override
      public ClassLoader getClassLoader(final Location location) {
        return new SecureClassLoader() {

          @Override
          protected Class<?> findClass(final String className) throws ClassNotFoundException {
            final ByteArrayOutputStream bos = byteStreams.get(className);
            if (bos == null) {
              return null;
            }
            final byte[] b = bos.toByteArray();
            return super.defineClass(className, b, 0, b.length);
          }
        };
      }

      @Override
      public JavaFileObject getJavaFileForOutput(final Location location, final String className, final JavaFileObject.Kind kind, final FileObject sibling) throws IOException {

        return new SimpleJavaFileObject(URI.create("string:///" + className.replace('.', '/') + kind.extension), kind) {
          @Override
          public OutputStream openOutputStream() throws IOException {
            ByteArrayOutputStream bos = byteStreams.get(className);
            if (bos == null) {
              bos = new ByteArrayOutputStream();
              byteStreams.put(className, bos);
            }
            return bos;
          }
        };

      }
    };
  }

  public boolean compile() {
    if (!valid) {
        return false;
    }
    final List<JavaFileObject> files = new ArrayList<>();
    for (IMCSourceCode classSourceCode : classSourceCodes) {
        URI uri = null;
        try {
            uri = URI.create("string:///" + classSourceCode.fullClassName.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension);
        } catch (Exception e) {
                           e.printStackTrace();//////////
        }
        if (uri != null) {
            final SimpleJavaFileObject sjfo = new SimpleJavaFileObject(uri, JavaFileObject.Kind.SOURCE) {

                @Override
                public CharSequence getCharContent(final boolean ignoreEncodingErrors) {

                    return classSourceCode.sourceCode;
                }
            };
            files.add(sjfo);
        }
    }

    final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

    final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    if (files.size() > 0) {
      final JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, files);
      boolean ok = task.call();
      if (!ok) {
        for (var diag : diagnostics.getDiagnostics()) {
          System.out.println(diag);
        }
        return false;
      }
    }
    return true;
  }

  @SuppressWarnings("deprecation")
  public void runToString(final String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

      if (!valid) {
          return;
      }
      final Class<?> theClass = getCompiledClass(className);
      final Object instance = theClass.newInstance();
      System.out.println(instance);
  }

  public void runMain(final String className, final String[] args) throws IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {

      if (!valid) {
          return;
      }
      final Class<?> theClass = getCompiledClass(className);
      final Method mainMethod = theClass.getDeclaredMethod("main", String[].class);
      mainMethod.invoke(null, new Object[] { args });
  }

  public Class<?> getCompiledClass(final String className) throws ClassNotFoundException {

      if (!valid) {
          throw new IllegalStateException("InMemoryCompiler instance not usable because ToolProvider.getSystemJavaCompiler() returned null: No JDK installed.");
      }
      final ClassLoader classLoader = fileManager.getClassLoader(null);
      final Class<?> ret = classLoader.loadClass(className);
      if (ret == null) {
          throw new ClassNotFoundException("Class returned by ClassLoader was null!");
      }
      return ret;
  }
}

