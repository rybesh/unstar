package unstar;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import org.apache.jena.graph.Graph;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.system.RDFStar;

public class App {

    public static void main(String[] args) {

        if (args.length == 1) {
            Path path = Paths.get(args[0]);
            if (checkPath(path)) {
                Model model = RDFDataMgr.loadModel(path.toString());
                Graph reified = RDFStar.encodeAsRDF(model.getGraph());
                RDFDataMgr.write(System.out, reified, Lang.TURTLE);
                System.exit(0);
            }
        }
        usage();
        System.exit(1);
    }

    private static boolean checkPath(Path p) {
        if (Files.isReadable(p)) {
            return true;
        } else {
            return err("Cannot read %s", p);
        }
    }

    private static boolean err(String msgTemplate, Path p) {
        System.err.println(String.format(msgTemplate, p));
        return false;
    }

    private static void usage() {
        System.err.println();
        System.err.println("usage:");
        System.err.println("  unstar file");
        System.err.println();
        System.err.println("  `file` should be an RDF-star file");
        System.err.println();
    }
}
