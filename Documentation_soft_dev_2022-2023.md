## ergasia_omadas_5
ΤΕΧΝΟΛΟΓΙΑ ΛΟΓΙΣΜΙΚΟΥ | ΘΕΜΑ ΕΡΓΑΣΙΑΣ ΟΜΑΔΑΣ 5 2022-2023

---
### Επιμέλια
- Αλέξανδρος Δημητριάδης p20055
- Γιώργος Πατρικάκος p20156
- Νικόλαος Σταυράκης p18233
- Χρήστος Μόσχος p16188

---
### Εισαγωγή

- [Στόχοι εργασίας](#στόχοι)
- [Ορισμός του προβλήματος προς επίλυση](#ορισμός)

---
### Παρουσίαση RUP 
[Σύντομη παρουσίαση της RUP](#rup)

---

### Φάση: έναρξης (Inception)

[Σύλληψη απαιτήσεων](#απαιτήσεις)
[Ανάλυση-Σχεδιασμός](#ανάλυση-σχεδιασμός)
- [Διαγράμματα Περιπτώσεων Χρήσης](#διαγράμματα-περιπτώσεων-χρήσης)
- [Διαγράμματα Τάξεων](#διαγράμματα-τάξεων)

---

### Φάση: εκπόνησης μελέτης (elaboration)
- Ανάλυση-Σχεδιασμός
	- [Διαγράμματα Περιπτώσεων Χρήσης (2η έκδοση)](#διάγραμμα-χρήσης-v2)
	- [Διαγράμματα Τάξεων  (2η έκδοση)](#διάγραμμα-τάξεων-v2)
	- [Διαγράμματα Αντικειμένων (1η έκδοση)](#διάγραμμα-αντικειμένων-v1)
	- [Διαγράμματα Συνεργασίας (1η έκδοση)](#διάγραμμα-συνεργασίας-v1)
	- [Διαγράμματα Σειράς (1η έκδοση)](#διάγραμμα-σειράς-v1)
	- [Διαγράμματα Δραστηριοτήτων (1η έκδοση)](#διάγραμμα-δραστηριοτήτων-v1)
	- [Διαγράμματα Καταστάσεων (1η έκδοση)](#διάγραμμα-καταστάσεων-v1)
	- [Διαγράμματα Εξαρτημάτων (1η έκδοση)](#διάγραμμα-εξαρτημάτων-v1)
	- [Διαγράμματα Διανομής (1η έκδοση)](#διάγραμμα-διανομής-v1)
- Υλοποίηση-Έλεγχος
	- [Υλοποίηση: 1η εκτελέσιμη έκδοση](#υλοποίηση-v1)
	- [Αναφορά ελέγχου για την 1η εκτελέσιμη έκδοση](#αναφορά-ελέγχου-v1) 
	  
---

### Φάση: Κατασκευή (Construction)
- Ανάλυση-Σχεδιασμός
	- [Διαγράμματα Περιπτώσεων Χρήσης (3η έκδοση)](#διάγραμμα-χρήσης-v3)
	- [Διαγράμματα Τάξεων (3η έκδοση)](#διάγραμμα-τάξεων-v3)
	- [Διαγράμματα Αντικειμένων (2η έκδοση)](#διάγραμμα-αντικειμένων-v2)
	- [Διαγράμματα Συνεργασίας (2η έκδοση)](#διάγραμμα-συνεργασίας-v2)
	- [Διαγράμματα Σειράς (2η έκδοση)](#διάγραμμα-σειράς-v2)
	- [Διαγράμματα Δραστηριοτήτων (2η έκδοση)](#διάγραμμα-δραστηριοτήτων-v2)
	- [Διαγράμματα Καταστάσεων (2η έκδοση)](#διάγραμμα-καταστάσεων-v2)
	- [Διαγράμματα Εξαρτημάτων (2η έκδοση)](#διάγραμμα-εξαρτημάτων-v2)
	- [Διαγράμματα Διανομής (2η έκδοση)](#διάγραμμα-διανομής-v2)
- Υλοποίηση-Έλεγχος
	- [Υλοποίηση: Tελική εκτελέσιμη έκδοση](#υλοποίηση-τελική)
	- [Αναφορά ελέγχου για την τελική εκτελέσιμη έκδοση](#αναφορά-ελέγχου-τελική) 
	  

---

### [Εγχειρίδιο Χρήστη](CodeTaskUserManual.pdf)

---
### Στόχοι

Δημιουργία Web Εφαρμογής: Ο βασικός στόχος της εργασίας είναι η δημιουργία μιας λειτουργικής web εφαρμογής.

Υποστήριξη Τριών Τύπων Χρηστών: Η εφαρμογή θα πρέπει να υποστηρίζει δύο βασικούς τύπους χρηστών: τους πελάτες, τους developers, τον admin.

Ανάθεση και Υλοποίηση Έργων: Η εφαρμογή θα επιτρέπει στους πελάτες να διατυπώνουν έργα που θέλουν να υλοποιηθούν και στους developers να καταχωρούν προσφορές για την υλοποίησή τους.Οι χρήστες θα έρχοντε σε επαφή με developers για να εκπληρώσουν κάποιο έργο που θέλουν. Ειδικότερα ο χρήστης θα μπορεί να ανεβάσει ένα task στο οποίο ζητάει απο κάποιον developer να το περατώσει. Η διαδικασία θα εμπεριέχει "δημοπρασία" όπου ο client/χρήστης, θα μπορεί να θέτει το ποσό που προτίθετε να πληρώσει για την περάτωση του έργου.

Διαχείριση Προσφορών: Η εφαρμογή θα πρέπει να επιτρέπει τη διαχείριση προσφορών από τους developers και την επιλογή της προσφοράς που θα ικανοποιεί τον πελάτη.

Ευχρηστία και Απόδοση: Η εφαρμογή θα πρέπει να είναι ευχρηστική και να παρέχει γρήγορη απόκριση, εξασφαλίζοντας καλή εμπειρία χρήστη.

Ασφάλεια Δεδομένων: Η ασφάλεια των δεδομένων των χρηστών και η προστασία της ιδιωτικότητάς τους είναι προτεραιότητα.

Επεκτασιμότητα: Η εφαρμογή θα πρέπει να είναι επεκτάσιμη για μελλοντικές επεκτάσεις και προσθήκες λειτουργιών.

Οπτική Αισθητική: Η εμφάνιση της εφαρμογής πρέπει να είναι ελκυστική και επαγγελματική.

Συμμόρφωση με τις Απαιτήσεις των Χρηστών: Οι στόχοι της εφαρμογής πρέπει να πληρούν τις απαιτήσεις και τις ανάγκες των πελατών και των developers.

---
### Ορισμός

Πρόβλημα: 
Το πρόβλημα που πρέπει να επιλυθεί είναι η ανάγκη για μια web εφαρμογή που θα διευκολύνει τη διαδικασία δημιουργίας και ανάθεσης έργων μεταξύ πελατών και developers.

Ανάγκες και Αναφορά στην Εργασία:
Οι πελάτες χρειάζονται έναν τρόπο να διατυπώνουν τα έργα που επιθυμούν να υλοποιηθούν, περιγράφοντας τις απαιτήσεις τους. Οι developers χρειάζονται έναν τρόπο να ενημερώνονται για τα διαθέσιμα έργα και να δίνουν προσφορές για την υλοποίησή τους. Οι πελάτες πρέπει να έχουν τη δυνατότητα να επιλέγουν την καλύτερη προσφορά από τους developers. Η εφαρμογή πρέπει να παρέχει ασφάλεια στα δεδομένα των χρηστών και να προστατεύει την ιδιωτικότητά τους. Η χρήση της εφαρμογής πρέπει να είναι ευχρηστική και αποδοτική. Είναι σημαντικό να υπάρχει η δυνατότητα επέκτασης της εφαρμογής για την ενσωμάτωση μελλοντικών λειτουργιών. Η εμφάνιση της εφαρμογής πρέπει να είναι ελκυστική και να ενισχύει την επαγγελματική εικόνα. Η εφαρμογή που θα αναπτυχθεί θα πρέπει να ανταποκρίνεται σε αυτές τις ανάγκες και απαιτήσεις, επιτρέποντας στους πελάτες και τους developers να συνεργάζονται αποτελεσματικά και αξιόπιστα για την υλοποίηση των έργων.

---
### RUP

Γενική Διαδικασία Συνεκτικής Σχεδίασης (RUP)

1. **Φάση Έναρξης:**

Αναγνώριση Ενδιαφερομένων: Αναγνωρίστε τους κύριους ενδιαφερόμενους και τις ανησυχίες τους. Όραμα και Επιχειρηματική Περίπτωση: Ορίστε το όραμα του έργου και δημιουργήστε μια επιχειρηματική περίπτωση. Μελέτη Εφικτότητας: Αξιολογήστε την τεχνική, οικονομική και λειτουργική εφικτότητα. Αξιολόγηση Κινδύνων: Αναγνωρίστε και αναλύστε τους κινδύνους του έργου. Task  mapping: Δημιουργήστε έναν χάρτη έργου για την επίσημη έναρξη του έργου.

2. **Φάση Επεξεργασίας:**

Συλλογή Απαιτήσεων: Συλλέξτε και τεκμηριώστε λεπτομερείς απαιτήσεις. Αρχιτεκτονικός Σχεδιασμός: Αναπτύξτε την αρχιτεκτονική του συστήματος. Πρωτότυπα: Δημιουργήστε πρωτότυπα για την επικύρωση των σχεδιαστικών έννοιων. Αντιμετώπιση Κινδύνων: Αντιμετωπίστε και μειώστε τους υψηλής προτεραιότητας κινδύνους. Ενημερωμένη Επιχειρηματική Περίπτωση: Αναθεωρήστε και ενημερώστε την επιχειρηματική περίπτωση βάσει των ακριβέστερων πληροφοριών. Επαναληπτική Ανάπτυξη: Ξεκινήστε επαναληπτικούς κύκλους ανάπτυξης.

3. **Φάση Κατασκευής:**

Κωδικοποίηση και Υλοποίηση: Γράψτε κώδικα βάσει του σχεδιασμού. Δοκιμές: Πραγματοποιήστε δοκιμές μονάδας, δοκιμές ενσωμάτωσης και δοκιμές συστήματος. Τεκμηρίωση: Δημιουργήστε εγχειρίδια χρήστη, τεχνική τεκμηρίωση και υλικό εκπαίδευσης. Εξασφάλιση Ποιότητας: Παρακολουθείτε συνεχώς και βελτιώστε τη διαδικασία ανάπτυξης. Προγραμματισμός Αναπτυξης: Προγραμματίστε την ανάπτυξη του συστήματος.

4. **Φάση Μετάβασης:**

Εκπαίδευση Χρηστών: Εκπαιδεύστε τους τελικούς χρήστες και το προσωπικό υποστήριξης. Δοκιμές Αποδοχής από τον Χρήστη: Επικυρώστε το σύστημα με τους τελικούς χρήστες. Κατανομή: Κατανείμετε το σύστημα στο περιβάλλον παραγωγής. Υποστήριξη Μετά την Κατανομή: Παρέχετε συνεχή υποστήριξη και συντήρηση. Κλείσιμο Έργου: Διεξαγάγετε μια ανασκόπηση έργου και τεκμηριώστε τα διδάγματα που αποκομίστηκαν.

5. **Φάση Συντήρησης:**

Επιδιόρθωση Βλαβών και Ενημερώσεις: Αντιμετωπίστε και διορθώστε τα θέματα που εντοπίστηκαν μετά την κατανομή. Αιτήσεις Αλλαγής: Αξιολογήστε και εφαρμόστε αιτήσεις αλλαγής. Παρακολούθηση Επίδοσης: Παρακολουθείτε συνεχώς την απόδοση του συστήματος. Ενημέρωση Τεκμηρίωσης: Κρατήστε την τεκμηρίωση ενημερωμένη. Μακροπρόθεσμη Υποστήριξη: Παρέχετε συνεχή υποστήριξη όπως απαιτείται.

6. **Επαναληπτική και Σταδιακή Ανάπτυξη:**

Σε όλες τις φάσεις, χρησιμοποιήστε επαναληπτικούς κύκλους ανάπτυξης για να τελειοποιήσετε το σύστημα βάσει των ανατροφοδοτήσεων και των μεταβαλλόμενων απαιτήσεων. 

7. **Διαχείριση Συνδυασμών και Αλλαγών:**

Εφαρμόστε διαδικασίες για τη διαχείριση αντικειμένων συνδυασμού και την αντιμετώπιση αιτημάτων αλλαγής. 

8. **Διαχείριση Κινδύνων:**

Αναγνωρίστε, αξιολογήστε και μειώστε συνεχώς τους κινδύνους του έργου. 

9. **Εξασφάλιση Ποιότητας:**

Εφαρμόστε διαδικασίες εξασφάλισης ποιότητας για να εξασφαλίσετε ότι το τελικό προϊόν πληροί τα πρότυπα ποιότητας. 

10. **Διαχείριση Έργου:**

- Διαχειριστείτε το έργο χρησιμοποιώντας καλά καθορισμένες διαδικασίες διαχείρισης έργου και εργαλεία.

11. **Συνεργασία και Επικοινωνία:**

- Προωθήστε τη συνεργασία μεταξύ μελών της ομάδας και των ενδιαφερόμενων μέσω τακτικής επικοινωνίας.

12. **Προσαρμογή:**

- Προσαρμόστε τη διαδικασία RUP στις συγκεκριμένες ανάγκες και περιορισμούς του έργου.

Η RUP είναι υψηλά προσαρμόσιμη και μπορεί να προσαρμοστεί για να ταιριάζει στις μοναδικές απαιτήσεις διαφορετικών έργων. Επισημαίνει την επαναληπτική ανάπτυξη, τη διαχείριση κινδύνων και την έντονη έμφαση στην αρχιτεκτονική και τον σχεδιασμό, καθιστώντας το ένα ευέλικτο πλαίσιο για την ανάπτυξη λογισμικού.

----
### Inception


#### Απαιτήσεις

Σύλληψη απαιτήσεων Βάσει της περιγραφής της εργασίας, μπορούμε να συνοψίσουμε τις απαιτήσεις που προκύπτουν ως εξής: 

Δημιουργία Web Εφαρμογής: Πρέπει να δημιουργηθεί μια λειτουργική web εφαρμογή που θα υλοποιεί τη διαδικασία δημιουργίας και ανάθεσης έργων.\
Χρήστες (Developers και Πελάτες): Η εφαρμογή πρέπει να υποστηρίζει δύο βασικούς χρήστες, τους developers και τους πελάτες, με διαφορετικούς ρόλους και λειτουργίες.

Διαχείριση Έργων: Οι πελάτες πρέπει να έχουν τη δυνατότητα να διατυπώνουν έργα που επιθυμούν να υλοποιηθούν, περιγράφοντας τις απαιτήσεις τους. Οι developers πρέπει να έχουν πρόσβαση σε αυτά τα έργα και να μπορούν να υποβάλλουν προσφορές για την υλοποίησή τους.

Επιλογή Προσφοράς: Οι πελάτες πρέπει να μπορούν να επιλέγουν την προσφορά από τους developers που τους ικανοποιεί περισσότερο.

Παρακολούθηση και Ολοκλήρωση Έργων: Η εφαρμογή πρέπει να παρέχει δυνατότητες παρακολούθησης της προόδου και της ολοκλήρωσης των έργων που υλοποιούνται.\
Ασφάλεια Δεδομένων: Πρέπει να ληφθούν μέτρα ασφαλείας για την προστασία των δεδομένων των χρηστών και τη διασφάλιση της ιδιωτικότητάς τους.

Ευχρηστία και Απόδοση: Η εφαρμογή πρέπει να είναι ευχρηστική και να παρέχει γρήγορη απόκριση, προσφέροντας καλή εμπειρία χρήστη.\
Επεκτασιμότητα: Πρέπει να υπάρχει δυνατότητα επέκτασης της εφαρμογής για μελλοντικές επεκτάσεις και προσθήκες λειτουργιών.

Οπτική Αισθητική: Η εμφάνιση της εφαρμογής πρέπει να είναι ελκυστική και επαγγελματική, προσφέροντας μια καλή πρώτη εντύπωση.

---
### Ανάλυση-σχεδιασμός

##### **Διαγράμματα-περιπτώσεων-χρήσης**
![Use Case 1](https://github.com/yixiatros/ergasia_omadas_5/assets/26073247/d260e74a-3af6-4d61-a0fa-c85e6d65fccb)

1.	**Κοινές Λειτουργίες Πελάτη-Developer (Common Functions):**
	
 	**Εγγραφή (Register) / Σύνδεση (Login):** Ο τρέχον χρήστης μπορεί να δημιουργήσει έναν λογαριασμό στην εφαρμογή με τη χρήση των προσωπικών του στοιχείων. 	Μετά την εγγραφή, μπορεί να συνδεθεί στον προσωπικό του λογαριασμό.\
	**Προβολή Προφίλ (View Profile):** Ο τρέχον χρήστης μπορεί να προβάλει το προφίλ του με προσωπικές πληροφορίες, αλλά και προφίλ άλλων χρηστών.\
	**Προβολή Έργων (View Projects):** Μπορεί να προβάλει τα διαθέσιμα έργα και τις σχετικές πληροφορίες.\
	**Επεξεργασία Προφίλ (Edit Profile):** Ο τρέχον χρήστης μπορεί να επεξεργαστεί το προφίλ του και να ενημερώσει τα στοιχεία του.\
	**Παρακολούθηση Έργου (Track Project):** Ο τρέχον χρήστης μπορεί να παρακολουθεί την πρόοδο και την εξέλιξη του έργου που έχει αναθέσει.

2.	**Λειτουργίες Πελάτη (Customer Functions):**
	
   	**Δημιουργία / Επεξεργασία Έργου (Create / Edit Project):** Ο πελάτης μπορεί να δημιουργήσει ένα νέο έργο που θέλει να υλοποιηθεί.\
	**Επιλογή Προσφοράς, ανάθεση έργου σε developer (Select Bid):** Μετά την αξιολόγηση των προσφορών, μπορεί να επιλέξει την προσφορά που τον ικανοποιεί.\
	**Υποβολή Κριτικής / Βαθμολογίας (Place Rating):** Ο πελάτης μπορεί να αξιολογήσει τον developer που ανέλαβε ένα δικό του έργο.\
	**Υποβολή πρότασης έργου σε developer (Recomendation):** Ο πελάτης μπορεί να προτείνει το έργο του σε κάποιο developer.

3.	**Λειτουργίες Developer (Developer Functions):**
	
 	**Υποβολή Προσφοράς (Submit / Update Bid):** Ο developer μπορεί να υποβάλει προσφορά για ένα έργο που τον ενδιαφέρει, και μπορεί να ενημερώσει την 		προσφορά του για ένα έργο, αν απαιτείται.\
	**Υποβολή πρότασης έργου σε άλλο Developer (Recomendation):** Ο developer μπορεί να προτείνει ένα έργο σε κάποιο άλλο developer.\
	**Αποδοχή έργου (Accept Project):** Ο developer μπορεί να αποδεχτεί έργου που του έχει ανατεθεί από πελάτη.

##### **Διαγράμματα-τάξεων**
![ClassDiagramV1](https://github.com/yixiatros/ergasia_omadas_5/blob/main/UML%20Diagrams/Version%201/ClassDiagramVersion1.png)

---
### Φάση: εκπόνησης μελέτης (elaboration)
- Ανάλυση-Σχεδιασμός
 #### Διάγραμμα-χρήσης-v2
 ![Use Case Diagram Version 2](https://github.com/yixiatros/ergasia_omadas_5/assets/26073247/92e924fa-ab38-4c11-908b-bbd3a8d5c605)

 #### Διάγραμμα-τάξεων-v2
 ![ClassDiagramVersion2](https://github.com/yixiatros/ergasia_omadas_5/assets/26073247/082dbd8f-2e42-47d5-8ba9-13e877f1bbd9)

 #### Διάγραμμα-αντικειμένων-v1
 ![Object Diagram Version 1](https://github.com/yixiatros/ergasia_omadas_5/assets/26073247/ef0e6fec-1b53-4087-8b2f-ae3ce653b440)
 
Στο παραπάνω διάγραμμα αποτυπώνονται οι βασικές σχέσεις μεταξύ των αντικειμένων: client, project, developer, recommendation, offer, comment, rating and review.

•	Ένας πελάτης μπορεί να δημιουργήσει πολλά έργα.\
•	Κάθε έργο μπορεί να έχει πολλές προσφορές από διάφορους developers.\
•	Κάθε προσφορά σχετίζεται με ένα συγκεκριμένο έργο και έναν developer.\
•	Ένας πελάτης μπορεί να επιλέξει μία από τις προσφορές για ένα έργο που δημιούργησε.


 #### Διάγραμμα-συνεργασίας-v1
 ![Collaboration Diagram1](https://github.com/yixiatros/ergasia_omadas_5/assets/26073247/b278504f-429d-4df0-bb41-d8719c1b721e)

 #### Διάγραμμα-σειράς-v1
 ![SequenceDiagramVersion1](https://github.com/yixiatros/ergasia_omadas_5/assets/26073247/20a9e4d9-7928-4131-8cc4-3d52c51ae0dc)

 #### Διάγραμμα-δραστηριοτήτων-v1
 ![Activity Diagram Version 1](https://github.com/yixiatros/ergasia_omadas_5/assets/26073247/9f80cd5c-514a-431d-8c3e-9a06b9929e71)

 #### Διάγραμμα-καταστάσεων-v1
 ![state machine 1](https://github.com/yixiatros/ergasia_omadas_5/assets/26073247/5225a1c1-559b-4bd5-995a-e8bc4f925f50)

 #### Διάγραμμα-εξαρτημάτων-v1
 ![ComponentDiagramVersion1](https://github.com/yixiatros/ergasia_omadas_5/assets/26073247/917853cd-37a3-42c0-b5c8-68d03e6b4596)

Το διάγραμμα εξαρτημάτων (component diagram), παρουσιάζει τη δομή της εφαρμογής και τις σχέσεις μεταξύ τωv διαφόρων εξαρτημάτων.
 
1.	**Χρήστες (Users):** Αυτό το εξάρτημα αντιπροσωπεύει τους δύο βασικούς χρήστες της εφαρμογής, δηλαδή τους developers και τους πελάτες, και είναι ένα γενικό εξάρτημα που συμπεριλαμβάνει όλους τους χρήστες.\
2.	**Έργα (Projects):** Αυτό το εξάρτημα αντιπροσωπεύει τα έργα που δημιουργούν οι πελάτες και για τα οποία οι developers δίνουν προσφορές. Περιλαμβάνει τα στοιχεία των έργων όπως τίτλος, περιγραφή και καταληκτική ημερομηνία.

 #### Διάγραμμα-διανομής-v1
 ![Deployment Diagram Version 1](https://github.com/yixiatros/ergasia_omadas_5/assets/26073247/f4362bdc-4a4a-4fa9-90b1-28e984d158d0)

Το Διάγραμμα Διανομής (Deployment Diagram) αντιπροσωπεύει τη φυσική διάταξη των εξαρτημάτων της εφαρμογής και τον τρόπο με τον οποίο αυτά τρέχουν σε διαφορετικά περιβάλλοντα.
 
1.	**Διακομιστής Εφαρμογής (Application Server):** Ο διακομιστής εφαρμογής φιλοξενεί την κύρια εφαρμογή. Είναι υπεύθυνος για την εκτέλεση του κώδικα της εφαρμογής και τη διαχείριση των αιτημάτων από τους χρήστες.\
2.	**Βάση Δεδομένων (Database Server):** Ο διακομιστής δεδομένων φιλοξενεί τη βάση δεδομένων της εφαρμογής. Περιλαμβάνει τις πληροφορίες των χρηστών, των έργων, των προσφορών και άλλων δεδομένων που απαιτούνται από την εφαρμογή.\
3.	**Διακομιστής Αυθεντικοποίησης (Authentication Server):** Ο διακομιστής αυθεντικοποίησης υπεύθυνος για την επαλήθευση ταυτότητας των χρηστών κατά τη σύνδεσή τους στην εφαρμογή.\
4.	**Χρήστης (User):** Οι τελικοί χρήστες της εφαρμογής, μπορούν να αποκτήσουν πρόσβαση στην εφαρμογή μέσω του web browser τους ή μέσω εφαρμογής για κινητά τηλέφωνα.

- Υλοποίηση-Έλεγχος
 #### Υλοποίηση-v1
  Κατά τη φάση υλοποίησης για την πρώτη εκτελέσιμη έκδοση της εφαρμογής, ο στόχος είναι η δημιουργία της βασικής δομής και λειτουργικότητα της εφαρμογής. Οι ακόλουθες δραστηριότητες πραγματοποιήθηκαν κατά τη διάρκεια αυτής της φάσης:

•	Δημιουργία της Βάσης Δεδομένων: Πραγματοποιήθηκε η σχεδίαση και δημιουργία της βάσης δεδομένων που απαιτείται για την αποθήκευση πληροφοριών σχετικά με τα έργα, τους πελάτες, τους developers κτλ. Χρησιμοποιήθηκαν αντικείμενα όπως τα διαγράμματα τάξεων και διαγράμματα αντικειμένων για τον σχεδιασμό της βάσης δεδομένων. Για βάση δεδομένων χρησιμοποιήθηκε η mySQL, καθώς και τα table αυτής δημιουργούνται αυτόματα με την εκτέλεση του προγράμματος/server με την χρήση των annotation του spring boot (πχ @Entity).\
•	Συνδεσιμότητα και Διασύνδεση: Οι διαφορετικές συνιστώσες της εφαρμογής συνδέθηκαν μεταξύ τους για να επιτρέψουν τη ροή των δεδομένων και τις διαφορετικές λειτουργίες της εφαρμογής. Δηλαδή χρησιμοποιήθηκε html/css/javascript και thymeleaf με σκοπό να επικοινωνήσουν τα προαναφερθέντα με την java.\
•	Υλοποίηση της Λειτουργικότητας Δημιουργίας Έργου: Επιτρέπεται στους πελάτες να δημιουργούν έργα, καθορίζοντας τις απαιτήσεις τους, όπως τον τύπο του έργου, τον προϋπολογισμό και τις προθεσμίες.\
•	Υλοποίηση της Λειτουργικότητας Υποβολής Προσφορών: Δίνεται η δυνατότητα στους developers να υποβάλουν προσφορές για τα έργα που τους ενδιαφέρουν, να επιλεχθούν από τους πελάτες, να αποδεχθούν τα έργα, και τέλος ο πελάτης και ο developer να ολοκληρώσουν το έργο.\
•	Υλοποίηση της Λειτουργικότητας Αναζήτηση Χρηστών και έργων: Οι χρήστες αποκτούν την δυνατότητα να αναζητούν έργα και άλλους χρήστες. Για την διευκόλυνση τους η εφαρμογή διαθέτη την δυνατότητα αναζήτησης μέσω searchbar αλλά και φίλτρα όπως η κατηγορίες και υποκατηγορίες των έργων.

  Οι παραπάνω είναι μόνο μερικά από τα βήματα που πραγματοποιήθηκαν κατά την υλοποίηση της πρώτης εκτελέσιμης έκδοσης της εφαρμογής. Η διαδικασία περιλάμβανε επίσης τον έλεγχο των εξαρτημάτων και την προετοιμασία για τον επόμενο γύρο ανάπτυξης και δοκιμών.
 
 #### Αναφορά-ελέγχου-v1

  Μετά την ανάπτυξη της πρώτης εκτελέσιμης έκδοσης της εφαρμογής, πραγματοποιήθηκε εκτενής έλεγχος για την επιβεβαίωση της ποιότητας και της συμμόρφωσής της με τις προδιαγραφές και τις απαιτήσεις του έργου. Ο έλεγχος περιλάμβανε τις εξής δραστηριότητες:
 
•	Δοκιμές Λειτουργικότητας: Εκτελέστηκαν αρκετές δοκιμές για να εξασφαλιστεί ότι η εφαρμογή λειτουργεί σωστά σε όλα τα επίπεδα της βασικής λειτουργικότητάς της. Συμπεριλαμβάνονταν οι διαδικασίες δημιουργίας έργων από τους πελάτες, υποβολής προσφορών από τους developers, καθώς και η επιλογή προσφορών από τους πελάτες.\
•	Δοκιμές Συμπεριφοράς: Ελέγχθηκε η συμπεριφορά της εφαρμογής σύμφωνα με τις αναμενόμενες προδιαγραφές. Αυτό περιλάμβανε την ανταπόκριση της εφαρμογής σε αναμενόμενες ενέργειες χρηστών, όπως η διαχείριση σφαλμάτων και η αποτελεσματική ανακατεύθυνση των χρηστών σε περιπτώσεις προβλημάτων.\
•	Δοκιμές Ασφάλειας: Πραγματοποιήθηκαν δοκιμές ασφαλείας για τον έλεγχο πιθανών αδυναμιών ασφαλείας στην εφαρμογή, όπως ευπάθειες στα authentication/authorization και πρόσβαση σε ανεπιθύμητες πληροφορίες.

  Τονίζεται ότι η αναφορά ελέγχου αποτελεί μόνο μια ενδεικτική αναφορά και ότι οι διαδικασίες ελέγχου και δοκιμών πρέπει να είναι επαρκείς για την διασφάλιση της ποιότητας και της λειτουργικότητας της εφαρμογής. Τα ενδεχόμενα προβλήματα που παρουσιάστηκαν καταγράφηκαν και αντιμετωπίστηκαν κατάλληλα.

---

### Φάση: Κατασκευή  (Construction)
- Ανάλυση-Σχεδιασμός
 #### Διάγραμμα-χρήσης-v3
Η τελευταία έκδοση της εφαρμογής απαιτεί τη βελτιστοποίηση των ήδη υπαρχουσών λειτουργιών αντί για την προσθήκη επιμέρους μεμονωμένων χαρακτηριστικών. Ως εκ τούτου, η ενημέρωση του διαγράμματος περιπτώσεων χρήσης v2 καθίσταται αδύνατη, καθώς η τελική έκδοση δεν θα εισάγει νέες δυνατότητες στους χρήστες, αλλά θα ενισχύσει τις ήδη υπάρχουσες.

 #### Διάγραμμα-τάξεων-v3
 ![ClassDiagramVersion3](https://github.com/yixiatros/ergasia_omadas_5/assets/26073247/253b0865-984d-43f2-9f29-aea819cb8cf7)

 
 #### Διάγραμμα-αντικειμένων-v2
 ![Object Diagram Version 2](https://github.com/yixiatros/ergasia_omadas_5/assets/26073247/d5e6a017-52f3-4ccc-a9dd-4e8166abad2b)

 #### Διάγραμμα-συνεργασίας-v2
 ![Collaboration Diagram Version 2](https://github.com/yixiatros/ergasia_omadas_5/assets/26073247/871d5a7e-7381-4736-ad03-bea863d6be32)

 #### Διάγραμμα-σειράς-v2
 ![Sequence Diagram Version 2](https://github.com/yixiatros/ergasia_omadas_5/assets/26073247/75cc2a8e-effa-48d3-8bc2-82d3f269f4d4)

 #### Διάγραμμα-δραστηριοτήτων-v2
 ![Activity Diagram Version 2](https://github.com/yixiatros/ergasia_omadas_5/assets/26073247/af8ccc6f-60c0-4634-a6c3-6729da3b79fb)

 #### Διάγραμμα-καταστάσεων-v2
 ![State Machine Diagram Version 2](https://github.com/yixiatros/ergasia_omadas_5/assets/26073247/01588cfb-5f51-4c4f-9e27-d2c052bc5f65)

 #### Διάγραμμα-εξαρτημάτων-v2
 ![ComponentDiagramVersion2](https://github.com/yixiatros/ergasia_omadas_5/assets/26073247/7cdc8c43-576c-4b15-8518-29fa79e76c3e)

 #### Διάγραμμα-διανομής-v2
 ![Deployment Diagram Version 2](https://github.com/yixiatros/ergasia_omadas_5/assets/26073247/2f585848-a625-4ddd-bffa-ece7153a8008)

- Υλοποίηση-Έλεγχος
 #### υλοποίηση-τελική

  Κατά τη φάση της τελικής εκτελέσιμης έκδοσης της εφαρμογής, το επίκεντρο ήταν η ολοκλήρωση και βελτίωση της λειτουργικότητας και της απόδοσης της εφαρμογής, καθώς και επιδιόρθωση τυχών σφαλμάτων η δυσλειτουργιών. Ακολούθως περιγράφονται τα κύρια βήματα που πραγματοποιήθηκαν κατά τη διάρκεια της υλοποίησης:

•	**Διαχείριση Σφαλμάτων:** Πραγματοποιήθηκε έλεγχος της εφαρμογής για την ανίχνευση και απομάκρυνση των σφαλμάτων και των προβλημάτων λειτουργίας. Τα σφάλματα αναφέρθηκαν, τεκμηριώθηκαν, και αναλύθηκαν για την εύρεση μιας βέλτιστης λύσης.\
•	**Διασφάλιση Ασφάλειας:** Πραγματοποιήθηκαν επιπλέον δοκιμές ασφαλείας για την επιβεβαίωση της ασφάλειας της εφαρμογής. Ελέγχθηκαν τυχόν ευπάθειες και απειλές και προστέθηκαν περαιτέρω μέτρα ασφαλείας.\
•	**Προετοιμασία για Κυκλοφορία:** Πραγματοποιήθηκαν όλες οι προετοιμασίες για την κυκλοφορία της εφαρμογής, συμπεριλαμβανομένης της διαμόρφωσης του διακομιστή, και την τροποποίηση της οριστικής βάσης δεδομένων.\
•	**Τεστ και Επαλήθευση:** Πραγματοποιήθηκαν τεστ απόδοσης και επαλήθευσης για την επιβεβαίωση ότι η τελική εκτελέσιμη έκδοση της εφαρμογής λειτουργεί χωρίς προβλήματα και ανταποκρίνεται αποτελεσματικά σε διάφορες καταστάσεις.\
•	**Προετοιμασία Τεκμηρίωσης:** Πραγματοποιήθηκε η δημιουργία της τεκμηρίωσης για την τελική εκτελέσιμη έκδοση της εφαρμογής, περιλαμβανομένων ενημερωμένων οδηγιών χρήσης.
 
 #### αναφορά-ελέγχου-τελική

  Κατά τη φάση της τελικής εκτελέσιμης έκδοσης της εφαρμογής, πραγματοποιήθηκε ελέγχος για τη διασφάλιση της ποιότητας και της ασφάλειας της εφαρμογής. Η αναφορά περιλαμβάνει τα εξής:
 
•	**Απόδοση και Αξιοπιστία:** Ελέγχθηκε η απόδοση της εφαρμογής και επιβεβαιώθηκε ότι λειτουργεί χωρίς σημαντικές καθυστερήσεις. Επίσης, ελέγχθηκε η αξιοπιστία της εφαρμογής, και διορθώθηκαν τυχόν σφάλματα που εντοπίστηκαν.\
•	**Ασφάλεια:** Πραγματοποιήθηκε έλεγχος ασφαλείας για την ανίχνευση ευπαθειών και αδυναμιών. Οποιεσδήποτε ανωμαλίες ή πιθανές απειλές αναφέρθηκαν, και λήφθηκαν τα απαραίτητα μέτρα για την αντιμετώπισή τους.\
•	**Συμμόρφωση με τις Προδιαγραφές:** Ελέγχθηκε αν η εφαρμογή συμμορφώνεται με τις προδιαγραφές και τις απαιτήσεις που ορίστηκαν στην αρχή του έργου, από την εκφώνηση αλλά και από την Ανάλυση-Σχεδιασμός που πραγματοποιήθηκε στην αρχική φάση της εφαρμογής.\
•	**Δοκιμές Χρήστη:** Διενεργήθηκαν δοκιμές χρήστη για την επαλήθευση ότι η εφαρμογή λειτουργεί σωστά και αποδίδει όπως αναμένεται από τους τελικούς χρήστες.
