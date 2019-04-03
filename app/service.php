<?php
define('DB_NAME', 'mariwhgc_gsb_frais');
define('DB_USER', 'mariwhgc_mpe');
define('DB_PASSWORD', 'Marie&Wordpress&21');
define('DB_HOST', 'localhost');

$action = $_REQUEST["action"];
global $dbh;

$dbh = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

Switch ($action) {
    
    case "getIdVisiteur":
        $login = $_REQUEST["login"];
        $mdp = $_REQUEST["mdp"];
        $req = "select * from visiteur where login = '$login'";

        $res = mysqli_query($dbh, $req);
        $row = mysqli_fetch_assoc($res);

        // vérification que le login correspond au mdp
        if ($mdp != $row["mdp"]) {
            echo json_encode(1);
        } else {
            echo json_encode($row, true);
        }
        break;

    case "getFichesDeFrais":
        $idVisiteur = $_REQUEST["idVisiteur"];
        $req = "select mois, idetat from fichefrais " . "where idvisiteur = '$idVisiteur'";
        $res = mysqli_query($dbh, $req);
        $row = mysqli_fetch_all($res);
        echo json_encode($row, true);
        break;

    case "creeFicheFrais":
        // xdebug_break();

        $idVisiteur = $_REQUEST["idVisiteur"];
        $mois = $_REQUEST["mois"];
        $moisPrecedent = $_REQUEST["moisPrecedent"];

        // Cloture derniere fiche
        $req = "update fichefrais set idetat = 'CL' " . "where idvisiteur = '$idVisiteur' and " . "mois = '$moisPrecedent' ";
        $res = mysqli_query($dbh, $req);

        // Création d'une nouvelle fiche pour le mois en cours
        $req = "insert into fichefrais (idvisiteur, mois, " . "nbjustificatifs, montantvalide, datemodif, idetat) " . "values ('$idVisiteur', '$mois', 0, 0, now(), 'CR')";
        $res = mysqli_query($dbh, $req);

        // création des lignes de frais forfait
        $lesFraisForfait = array(
            "ETP",
            "NUI",
            "REP"
        );
        foreach ($lesFraisForfait as $unFrais) {
            $req = "insert into lignefraisforfait (idvisiteur, " . "mois, idfraisforfait, quantite) values (" . "'$idVisiteur', '$mois', '$unFrais', 0)";
            $res = mysqli_query($dbh, $req);
        }

        // Création des lignes de frais kilométrique
        $lesFraisKm = array(
            "D4",
            "D6",
            "E4",
            "E6"
        );
        foreach ($lesFraisKm as $unIdFraisKm) {
            $req = "insert into lignefraisforfait (idvisiteur, " . "mois, idfraiskm, quantite) values (" . "'$idVisiteur', '$mois', '$unIdFraisKm', 0 )";
            $res = mysqli_query($dbh, $req);
        }
        break;

    case "getLignesFraisForfait":
        $idVisiteur = $_REQUEST["idVisiteur"];
        $req = "select * from lignefraisforfait where " . "idvisiteur = '$idVisiteur'";
        $res = mysqli_query($dbh, $req);
        $row = mysqli_fetch_all($res);

        echo json_encode($row, true);
        break;

    case "MAJligneFraisForfait":
        // xdebug_break();

        // récupéraition des données encoyées       
        $idVisiteur = $_REQUEST["idVisiteur"];
        $mois = $_REQUEST["mois"];
        $numero = $_REQUEST["numero"];
        $qte = $_REQUEST["qte"];
        // création de la requète de mise à jour de lignefraisforfait
        $req = "update lignefraisforfait set " . "quantite = $qte " . "where numero = $numero";
        $res = mysqli_query($dbh, $req);

        // Mise à jour de la fiche de frais (date de mise à jour)
        $req = "update fichefrais set " . "datemodif = now() " . "where idvisiteur = '$idVisiteur' and " . "mois = '$mois'";
        $res = mysqli_query($dbh, $req);
        break;

    case "getLignesFraisHF":
        $idVisiteur = $_REQUEST["idVisiteur"];
        $req = "select * from lignefraishorsforfait where " . "idvisiteur = '$idVisiteur'";
        $res = mysqli_query($dbh, $req);
        $row = mysqli_fetch_all($res);
        // encodage des libellés en UTF-8
        $size = count($row);
        for ($i = 0; $i < $size; $i ++) {
            $row[$i][3] = utf8_encode($row[$i][3]);
        }

        echo json_encode($row, JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE);
        break;
        
    case "suppLigneFraisHF":
        $id = $_REQUEST["id"];
        $req = "delete from lignefraishorsforfait "
            . "where id = '$id'";
        $res = mysqli_query($dbh,$req);
        break;
        
    case "creerLigneFraisHF":
        $idVisiteur = $_REQUEST["idVisiteur"];
        $mois = $_REQUEST["mois"];
        $libelle = utf8_decode($_REQUEST["libelle"]);
        $date = $_REQUEST["date"];
        $montant = $_REQUEST["montant"];
        $req = "insert into lignefraishorsforfait (idvisiteur, "
            . "mois, libelle, date, montant) values ('$idVisiteur', "
            . "'$mois', '$libelle', '$date', $montant)";
        $res = mysqli_query($dbh,$req);        
        break;
}

?>