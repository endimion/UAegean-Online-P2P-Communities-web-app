# Teem LoginWebApp

## English Version

This project was developed with funding form "Transformation of Greek e-Gov Services to eIDAS Crossborder Services", Agreement number: INEA/CEF/ICT/A2015/1147836 | Action No: 2015-EL-IA-0083, by the "Information Management Lab (i4M Lab)", which is part of the research group  "ATLANTIS Group".
- Information about "ATLANTIS Group" can be found: http://www.atlantis-group.gr/
<!-- - Για νέα σχετικά με την εξέλιξη του έργου επισκεφθείτε την ιστοσελίδα: https://ma.ellak.gr/forge/projects/jobselection_iap -->
<!-- /- Για την χρήση της εφαρμογής επισκεφθείτε την ιστοσελίδα: http://iap.atlantis-group.gr/TestJobSelection/home.php -->


### Project Scope

The Online Community Student Program facilities and services is a Service Provider that is focused on increasing the 
participation and sustainability 
of commons-based peer production student Projects like:
1. communities (e.g. wikis, makerspaces),
2. open online communities (networks, open organizations)
3. or social movements (social centers, collectives). 

The Online Community Student Program facilities and services is built using the open source framework Teem (https://teem.works/). 
Teem was built after substantial  social research and prototype testing, 
to address the different roles in a community as well as provide them with the tools they require. 
The framework tries to address these issues by reducing the frustrations of all participants and increase participation, 
while at the same time providing a kind-of project management tool for communities.
The Online Community Student Program facilities and services rewires the Teem User Management System and Authorization 
to grant access only to users that have been authenticated under eIDAS((https://ec.europa.eu/digital-single-market/en/policies/trust-services-and-eidentification)). 

The benefit from this variation is twofold. On the one hand, this modification of the framework shifts the burden from 
the users to maintain yet another electronic identity (and the credential management that inevitably follows it) 
and on the other hand, protects the created communities from unauthorized (and potential malicious) users, 
since each users is uniquely identified by eIDAS . 

To address the issue of authenticating and registering Teem users via eIDAS we developed a web application 
that acts as a middleware between the Teem framework and the eIDAS node. Specifically, this middleware WebApp 
handles both login/registration requests by the Teem framework and requests/responses to/from the eIDAS system. 

### Repository Contents [deliverables]

This repository is seperated in the following parts:

* **src**, this repository folder contains the microservice responsible for integrating the eIDAS infrastructure with Teem
 



### ReadMe.docx

Contains material relative to the development of the project. 
 It helps get a better understanding of the project and its code 
(https://docs.google.com/document/d/1FVaBHXhGiDHXXgyn6Nom1AmCGfw2wZv9Hz7igcWTt5E/edit?usp=sharing).


### Source Code

Contains the code used for the deployment of the service.
<!-- - https://github.com/ellak-monades-aristeias/jobselection_iap -->

## Greek Version

Το έργο αναπτύχθηκε για τις ανάγκες του έργου  "Transformation of Greek e-Gov Services to eIDAS Crossborder Services", Agreement number: INEA/CEF/ICT/A2015/1147836 | Action No: 2015-EL-IA-0083, από το "Information Management Lab (i4M Lab)", το οποίο αποτελεί μέρος της Ερευνητικής Ομάδας "ATLANTIS Group".

- Σχετικά με την Ερευνητική Ομάδα "ATLANTIS Group" επισκεφθείτε την ιστοσελίδα: http://www.atlantis-group.gr/
<!-- - Για νέα σχετικά με την εξέλιξη του έργου επισκεφθείτε την ιστοσελίδα: https://ma.ellak.gr/forge/projects/jobselection_iap -->
<!-- /- Για την χρήση της εφαρμογής επισκεφθείτε την ιστοσελίδα: http://iap.atlantis-group.gr/TestJobSelection/home.php -->


### Σκοπός του έργου

Tο Online Community Student Program facilities and services είναι ένα σύστημα παροχής υπηρεσιών το οποίο σκοπεύει να αυξήσει
τη συμμετοχή και τη βιωσιμότητα σε προτζεκτ που δημιουργούν οι φοιτητές παρέχοντας τους:
1. communities (π.χ. wikis, makerspaces),
2. open online communities (networks, open organizations)
3. or social movements (social centers, collectives). 

Το Online Community Student Program facilities and services έχει δημιουργηθεί χρησιμοποιώντας το ανοιτό λογισμικό Teem (https://teem.works/). 
Το Teem έχει φτιαχτεί μετά από συμαντική προσπάθεια και κοινωνική έρενα για να απαντσει στους διαφορετικούς ρόλους που χρειάζεται
ένα κοινωνικό πρόγραμμα και να του δώσει τα εργαλεία που χρειάζεται. Το Teem προσπαθεί να απαντήσει σε όλα τα ζητήματα που 
δημιουργούνται σε ένα τέτοιο πλαίσιο αλλά ταυτόχρονα να δώσει όλα τα αναγκαία  project management εργαλεία που χρειάζονται οι κοινώτητες που δημιουργεί.
Tο Online Community Student Program facilities and services διαφοροποιεί το σύστημα διαχείρησης των χρηστών του
Teem έτσι ώστε να επιτρέπεται μόνο η είσοδος σε χρήστες οι οποίοι έχουν πιστοποιηθεί με eIDAS((https://ec.europa.eu/digital-single-market/en/policies/trust-services-and-eidentification)). 

Τα πλεονεκτήματα από αυτό είναι διπλά. Από τη μία οι χρήστες δεν χρειάζεται να δημιουργήσουν έναν ακόμα λογαριασμό σε 
μία ακόμα υπηρεσία, από την άλλη οι κοινώτητες προστατεύονται από ανώνυμους (κακόβουλους) χρήστες . 

Για την υλοποίηση της πιστοποίησης μέσω eIDAS δημιουργήθηκε ένα web application το οποίο λειτουργεί σαν ενδιάμεσος μεταξύ 
του Teem και του eIDAS κόμβου. Συγκεκριμένα, από το πρόγραμμα διαχειρίζεται τη σύνδεση και εγγραφή των χρηστών στο Teem και τις
ερωτήσεις/απαντήσεις προς και από τον eIDAS κόμβο. 
### Περιεχόμενα του αποθετηρίου [Παραδοτέων]

Το αποθετήριο είναι χωρισμένο στα εξής μέρη:

* **src**, περιλαμβάνει ένα  microservice το οποίο διαχειρίζεται την διασύνδεση του Teem με τον κόμβο eIDAS


### ReadMe.docx

Περιλαμβάνει υλικό σχετικό με την ανάπτυξη του έργου, ιδιαίτερα χρήσιμο για την κατανόηση των αρχείων κώδικα. Σχετικά με τη λήψη του αρχείου αυτού επισκεφθείτε την ιστοσελίδα:
(https://docs.google.com/document/d/1FVaBHXhGiDHXXgyn6Nom1AmCGfw2wZv9Hz7igcWTt5E/edit?usp=sharing).
<!-- https://github.com/ellak-monades-aristeias/jobselection_iap/blob/master/ReadMe.docx -->

### Αρχεία κώδικα

Περιλαμβάνει τα αρχεία κώδικα που χρησιμοποιήθηκαν για την ανάπτυξη της εφαρμογής "Job Selection - IAP".
<!-- - https://github.com/ellak-monades-aristeias/jobselection_iap -->
