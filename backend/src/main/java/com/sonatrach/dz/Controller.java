package com.sonatrach.dz;

import java.awt.Cursor;

import java.io.BufferedReader;

import java.io.DataOutputStream;
import java.io.File

;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sonatrach.dz.archiveSentFiles.domain.ArchiveSentFiles;
import com.sonatrach.dz.archiveSentFiles.repo.ArchiveSentFilesRepo;
import com.sonatrach.dz.archiveSentGfiles.domain.ArchiveSentGfiles;
import com.sonatrach.dz.archiveSentGfiles.repo.ArchiveSentGfilesRepo;
import com.sonatrach.dz.archiveStructure.domain.ArchiveStructure;
import com.sonatrach.dz.archiveStructure.repo.ArchiveStructureRepo;
import com.sonatrach.dz.banque.domain.Banque;
import com.sonatrach.dz.banque.repo.BanqueRepo;
import com.sonatrach.dz.chang.domain.Change;
import com.sonatrach.dz.chang.repo.ChangRepo;
import com.sonatrach.dz.cloturePaie.domain.CloturePaie;
import com.sonatrach.dz.cloturePaie.repo.CloturePaieRepo;
import com.sonatrach.dz.dep.domain.Dep;
import com.sonatrach.dz.dep.repo.DepRepo;
import com.sonatrach.dz.diplome.domain.Diplome;
import com.sonatrach.dz.diplome.repo.DiplomeRepo;
import com.sonatrach.dz.efile.domain.Efile;
import com.sonatrach.dz.efile.repo.EfileRepo;
import com.sonatrach.dz.email.domain.MailRequest;
import com.sonatrach.dz.email.domain.MailResponse;
import com.sonatrach.dz.email.service.EmailService;
import com.sonatrach.dz.emailDB.domain.EmailDB;
import com.sonatrach.dz.emailDB.repo.EmailDbRepo;
import com.sonatrach.dz.etatJournal.domain.EtatJournal;
import com.sonatrach.dz.etatJournal.repo.EtatJournalRepo;
import com.sonatrach.dz.etatMand.domain.EtatMand;
import com.sonatrach.dz.etatMand.repo.EtatMandRepo;
import com.sonatrach.dz.etatMip.domain.EtatMip;
import com.sonatrach.dz.etatMip.repo.EtatMipRepo;
import com.sonatrach.dz.etatRecap.domain.DbulcrubSorter;
import com.sonatrach.dz.etatRecap.domain.DivSorter;
import com.sonatrach.dz.etatRecap.domain.EtatRecap;
import com.sonatrach.dz.etatRecap.domain.EtatRecap1;
import com.sonatrach.dz.etatRecap.repo.EtatRecapRepo;
import com.sonatrach.dz.etatRet.domain.EtatRet;
import com.sonatrach.dz.etatRet.repo.EtatRetRepo;
import com.sonatrach.dz.fileToPrint.domain.FileToPrint;
import com.sonatrach.dz.fileToPrint.repo.FileToPrintRepo;
import com.sonatrach.dz.fileType.domain.FileType;
import com.sonatrach.dz.fileType.repo.FileTypeRepo;
import com.sonatrach.dz.fileTypeToFolder.domain.FileTypeToFolder;
import com.sonatrach.dz.fileTypeToFolder.repo.FileTypeToFolderRepo;
import com.sonatrach.dz.folder.domain.Folder;
import com.sonatrach.dz.folder.repo.FolderRepo;
import com.sonatrach.dz.folderArchive.domain.FolderArchive;
import com.sonatrach.dz.folderArchive.repo.FolderArchiveRepo;
import com.sonatrach.dz.fonction.domain.Fonction;
import com.sonatrach.dz.fonction.repo.FonctionRepo;
import com.sonatrach.dz.gfile.domain.Gfile;
import com.sonatrach.dz.gfile.repo.GfileRepo;
import com.sonatrach.dz.localite.domain.Localite;
import com.sonatrach.dz.localite.repo.LocaliteRepo;
import com.sonatrach.dz.message.request.LoginForm;
import com.sonatrach.dz.message.request.SignUpForm;
import com.sonatrach.dz.message.response.JwtResponse;
import com.sonatrach.dz.message.response.ResponseMessage;
import com.sonatrach.dz.model.User;
import com.sonatrach.dz.newpaie.domain.NewPaie;
import com.sonatrach.dz.newpaie.repo.NewPaieRepo;
import com.sonatrach.dz.paymonth.domain.PayMonth;
import com.sonatrach.dz.paymonth.repo.PayMonthRepo;
import com.sonatrach.dz.pays.domain.Pays;
import com.sonatrach.dz.pays.repo.PaysRepo;
import com.sonatrach.dz.pers.domain.Pers;
import com.sonatrach.dz.pers.repo.PersRepo;
import com.sonatrach.dz.repository.UserRepository;
import com.sonatrach.dz.rubAlph.domain.RubAlph;
import com.sonatrach.dz.rubAlph.repo.RubAlphRepo;
import com.sonatrach.dz.rubNum.domain.RubNum;
import com.sonatrach.dz.rubNum.repo.RubNumRepo;
import com.sonatrach.dz.rubrique.domain.Rubrique;
import com.sonatrach.dz.rubrique.repo.RubriqueRepo;
import com.sonatrach.dz.security.jwt.JwtProvider;
import com.sonatrach.dz.shactivity.domain.ShActivity;
import com.sonatrach.dz.shactivity.repo.ShActivityRepo;
import com.sonatrach.dz.structure.domain.Structure;
import com.sonatrach.dz.structure.repo.StructureRepo;
import com.sonatrach.dz.tabstructure.domain.TabStructure;
import com.sonatrach.dz.tabstructure.repo.TabStructureRepo;

import ch.qos.logback.core.net.SyslogOutputStream;
import freemarker.template.TemplateException;
import io.jsonwebtoken.lang.Collections;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRCsvDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSwapFile;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

/*
 * Developped by : AID FERIEL for SONATRACH --2020--
 * Email:aidferiel@gmail.com
 */

@RestController
@CrossOrigin(origins = "*")
public class Controller {
	// **********************************************repositories************************************************************

	@Autowired
	BanqueRepo banqueRepo;
	@Autowired
	StructureRepo structureRepo;
	@Autowired
	CloturePaieRepo clotureRepo;
	@Autowired
	DiplomeRepo diplomeRepo;
	@Autowired
	FonctionRepo fonctionRepo;
	@Autowired
	LocaliteRepo localiteRepo;
	@Autowired
	PaysRepo paysRepo;
	@Autowired
	RubriqueRepo rubriqueRepo;
	@Autowired
	TabStructureRepo tabStructureRepo;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	DepRepo depRepo;
	@Autowired
	ChangRepo changRepo;
	@Autowired
	PayMonthRepo paymonthRepo;
	@Autowired
	NewPaieRepo newPaieRepo;
	@Autowired
	PersRepo persRepo;
	@Autowired
	RubNumRepo rubNumRepo;
	@Autowired
	FileToPrintRepo fileToPrintRepo;
	@Autowired
	ShActivityRepo shActivityRepo;
	@Autowired
	FolderRepo folderRepo;
	@Autowired
	ArchiveStructureRepo archiveStructureRepo;
	@Autowired
	EmailDbRepo emailDbRepo;
	@Autowired
	EfileRepo efileRepo;
	@Autowired
	private EmailService service;
	@Autowired
	ArchiveSentFilesRepo archiveSentFilesRepo;
	@Autowired
	FileTypeToFolderRepo fileTypeToFolderRepo;
	@Autowired
	FolderArchiveRepo folderArchiveRepo;
	@Autowired
	FileTypeRepo fileTypeRepo;
	@Autowired
	EtatJournalRepo etatJournalRepo;
	@Autowired
	EtatMandRepo etatMandRepo;
	@Autowired
	EtatMipRepo etatMipRepo;
	@Autowired
	EtatRetRepo etatRetRepo;
	@Autowired
	EtatRecapRepo etatRecapRepo;
	@Autowired
	GfileRepo gfileRepo;
	@Autowired
	ArchiveSentGfilesRepo archiveSentGfilesRepo;
	@Autowired
	RubAlphRepo rubAlphRepo;
	@Value("${grokonez.app.swap}")
	String SwapPath;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// ****************************************API*****************************************************************************

	
	// get structure by activity
	@PostMapping({ "getStructurByActivity" })
	public List<Structure> getStructureByActivity(@RequestBody ShActivity a) {
		List<Structure> lesStructures = new ArrayList();
		try {
			lesStructures = structureRepo.findByActivity(a.getIdactivity());

			return lesStructures;
		} catch (Exception e) {
			System.out.println("Exception getStructureByActivity()==>" + e.getMessage());
			lesStructures = null;
		}
		return lesStructures;
	}

	@GetMapping({ "/getCurrentMonth" })
	public PayMonth getCurrentMonth() {
		try {
			PayMonth currentDate = paymonthRepo.findByState();
			/*
			 * String currentYear = currentDate.getPaymonth().substring(0, 4); String
			 * currentMonth = currentDate.getPaymonth().substring(4, 6); String dateFormat =
			 * currentYear + "-" + currentMonth;
			 */
			return currentDate;
		} catch (Exception e) {
			System.out.println("Exception getCurrentMonth()==>" + e.getMessage());
		}
		return null;

	}

	/******************************************************************
	 * Historique
	 ****************************************************************************************************/
//get all emails sent by user
	@PostMapping({ "getAllEmails" })
	public List<EmailDB> getAllEmails(@RequestBody User user) {
		ArrayList emails = new ArrayList();
		try {
			emails = emailDbRepo.findByIdUser(user.getIduser(), 0);

			return emails;

		} catch (Exception e) {
			System.out.println("Exception getAllEmails()==>" + e.getMessage());
		}
		return null;
	}

//getFiles sent with email by user
	@PostMapping({ "getSentFiles" })
	public String[] getSentFiles(@RequestBody EmailDB email) {
		try {
			String[] fileNames = null;
			String[] gfileNames = null;
			ArrayList<ArchiveSentFiles> fileIds = archiveSentFilesRepo.findByIdEmail(email.getIdemail());
			ArrayList<ArchiveSentGfiles> gfilesIds = archiveSentGfilesRepo.findGfilesByIdEmail(email.getIdemail());

			if (fileIds != null) {

				fileNames = new String[fileIds.size()];
				for (int i = 0; i < fileIds.size(); i++) {
					Optional<Efile> sentFile = efileRepo.findById(fileIds.get(i).getIdfile());
					if (sentFile.get() != null) {
						fileNames[i] = sentFile.get().getFilename();
					}
				}

			}

			if (gfilesIds != null) {
				gfileNames = new String[gfilesIds.size()];

				for (int i = 0; i < gfilesIds.size(); i++) {
					Optional<Gfile> sentgFile = gfileRepo.findById(gfilesIds.get(i).getIdgfile());

					if (sentgFile.get() != null) {
						gfileNames[i] = sentgFile.get().getGfilename();

					}
				}

			}

			if (fileNames.length != 0) {
				return fileNames;
			} else {
				return gfileNames;
			}

		} catch (Exception e) {
			System.out.println("Exception  getSentFiles()==>" + e.getMessage());
		}

		return null;
	}

//get structure archive with operation
	@PostMapping({ "getArchiveStructure" })
	public List<ArchiveStructure> getArchiveStructure(@RequestBody User user) {
		try {
			ArrayList<ArchiveStructure> archiveStructure = new ArrayList();
			archiveStructure = archiveStructureRepo.findByOperation(user.getName(), user.getIduser(), -1);

			return archiveStructure;

		} catch (Exception e) {
			System.out.println("Exception  getArchiveStructure()==>" + e.getMessage());
		}
		return null;
	}

//get folder archive with operation
	@PostMapping({ "getArchiveFolder" })
	public List<FolderArchive> getArchiveFolder(@RequestBody User user) {
		try {
			ArrayList<FolderArchive> archiveFolder = new ArrayList();
			archiveFolder = folderArchiveRepo.findByOperation(user.getName(), user.getIduser(), -1);

			return archiveFolder;

		} catch (Exception e) {
			System.out.println("Exception  getArchiveStructure()==>" + e.getMessage());
		}
		return null;
	}

	// delete history emails
	@PostMapping({ "deleteHistoryEmails" })
	public List<EmailDB> deleteHistoryEmails(@RequestBody User user) {
		ArrayList<EmailDB> emails = new ArrayList();
		try {
			emails = emailDbRepo.findByIdUser(user.getIduser(), 0);

			for (int i = 0; i < emails.size(); i++) {
				emails.get(i).setEmailstatus(-1);
				emailDbRepo.save(emails.get(i));
			}
			return emails;
		} catch (Exception e) {
			System.out.println("Exception deleteHistoryEmails()==>" + e.getMessage());
		}
		return null;
	}

	// delete history structure
	@PostMapping({ "deleteHistoryStructure" })
	public List<ArchiveStructure> deleteHistoryStructure(@RequestBody User user) {
		try {
			ArrayList<ArchiveStructure> archiveStructure = new ArrayList();
			archiveStructure = archiveStructureRepo.findByUser(user.getIduser(), -1);

			for (int i = 0; i < archiveStructure.size(); i++) {
				archiveStructure.get(i).setArchstatusstructure(-1);
				archiveStructureRepo.save(archiveStructure.get(i));
			}
			return archiveStructure;

		} catch (Exception e) {
			System.out.println("Exception  deleteHistoryStructure()==>" + e.getMessage());
		}
		return null;
	}

	// delete history folders
	@PostMapping({ "deleteHistoryFolder" })
	public List<FolderArchive> deleteHistoryFolder(@RequestBody User user) {
		try {
			ArrayList<FolderArchive> archiveFolder = new ArrayList();
			archiveFolder = folderArchiveRepo.findByUser(user.getIduser(), -1);

			for (int i = 0; i < archiveFolder.size(); i++) {
				archiveFolder.get(i).setArchstatusfolder(-1);

				FolderArchive ar = folderArchiveRepo.save(archiveFolder.get(i));

			}
			return archiveFolder;

		} catch (Exception e) {
			System.out.println("Exception  deleteHistoryFolder()==>" + e.getMessage());
		}
		return null;
	}

	/*******************************************************
	 * Send Table files (email)
	 ************************************************************************************************/
	@PostMapping({ "sendEmailFiles" })
	public MailResponse sendEmailFiles(@RequestBody MailRequest request) {
		try {
			// to get folder path
			Folder folder = folderRepo.findByFolderName("TABLES");

			// *********get current date from payMonth

			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;

			String[] files = request.getFilesName();
			String[] fileNames = new String[files.length];
			for (int i = 0; i < files.length; i++) {
				fileNames[i] = files[i] + " " + dateFormat + ".xlsx";
			}
			if (folder != null) {
				String path = folder.getFOLDERPATH() + "TABLES" + File.separator + currentYear + File.separator
						+ dateFormat + File.separator;
				Map<String, Object> model = new HashMap<>();
				model.put("msg", request.getMsg());

				MailResponse response = service.sendEmailFiles(request, path, fileNames, model);
				return response;

			}
			return null;
		} catch (Exception e) {
			System.out.println("Exception while sending email files==>" + e.getMessage());
		}
		return null;
	}

	@GetMapping({ "getGfilesTable" })
	public List<Gfile> getGfilesTable() {
		try {
			List<Gfile> gfiles = new ArrayList();
			gfiles = gfileRepo.findByFolderName("TABLES");
			return gfiles;
		} catch (Exception e) {
			System.out.println("Exception  getGfilesTable()==>" + e.getMessage());

		}
		return null;
	}

	@PostMapping({ "saveArchiveSentGfiles" })
	public List<ArchiveSentGfiles> saveArchiveSentGfiles(@RequestBody List<ArchiveSentGfiles> files) {
		try {
			List<ArchiveSentGfiles> savedGfiles = new ArrayList();
			PayMonth currentDate = paymonthRepo.findByState();

			// System.out.println("saveEfile");
			for (int i = 0; i < files.size(); i++) {

				ArchiveSentGfiles file = archiveSentGfilesRepo.save(files.get(i));
				// System.out.println(file.getIdfile()+" ");
				savedGfiles.add(file);
			}
			return savedGfiles;
		} catch (Exception e) {
			System.out.println("Exception while saveArchiveSentGfiles()==>" + e.getMessage());
		}
		return null;
	}

	/******************************************************
	 * Etat Paie
	 ***************************************************************************************************************/
	// ************************************************compresser le fichier
	// zip*******************************************************************
	public String compressDirectory(String zipName, String path) {
		String sourceFile = path;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(zipName);
			ZipOutputStream zipOut = new ZipOutputStream(fos);
			File fileToZip = new File(sourceFile);
			String response = zipFile(fileToZip, fileToZip.getName(), zipOut);
			zipOut.close();
			fos.close();
			if (response.equals("yes")) {
				return zipName;
			} else {
				if (response.equals("no")) {
					return "error!" + zipName;
				}

			}

		} catch (FileNotFoundException e1) {
			System.out.println("exception in compressDirectory e1==>" + e1.getMessage());
		} catch (IOException e2) {
			System.out.println("exception in compressDirectory e2==>" + e2.getMessage());
		} catch (Exception e3) {
			System.out.println("exception in compressDirectory e3==>" + e3.getMessage());
		}

		return "faillure";

	}

	public String zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
		try {
			if (fileToZip.isHidden()) {
				return null;
			}
			if (fileToZip.isDirectory()) {
				if (fileName.endsWith("/")) {
					zipOut.putNextEntry(new ZipEntry(fileName));
					zipOut.closeEntry();
				} else {
					zipOut.putNextEntry(new ZipEntry(fileName + "/"));
					zipOut.closeEntry();
				}
				File[] children = fileToZip.listFiles();

				for (File childFile : children) {
					zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
				}
				// System.out.println(children.length);
				if (children.length != 8) {
					return "no";
				} else {
					return "yes";
				}

			}
			FileInputStream fis = new FileInputStream(fileToZip);
			ZipEntry zipEntry = new ZipEntry(fileName);
			zipOut.putNextEntry(zipEntry);
			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zipOut.write(bytes, 0, length);
			}
			fis.close();
		} catch (Exception e) {
			System.out.println("exception in method zipFile==>" + e.getMessage());
		}
		return "error";
	}

	// **********************************************************Envoyer les etats
	// des structures par mail (Etat
	// paie)***************************************************
	@PostMapping({ "sendEmailZip" })
	public MailResponse sendEmailZip(@RequestBody MailRequest request) {
		try {
			// to get folder path
			Folder folder = folderRepo.findByFolderName("ETAT");

			// *********get current date from payMonth

			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;

			if (folder != null) {
				String path = folder.getFOLDERPATH() + "ETAT" + File.separator + currentYear + File.separator
						+ dateFormat + File.separator + request.getSturcturename() + " " + dateFormat;
				String zipPathWithName = folder.getFOLDERPATH() + "ETAT" + File.separator + currentYear + File.separator
						+ dateFormat + File.separator + request.getSturcturename() + " " + dateFormat + ".zip";
				String zipName = request.getSturcturename() + " " + dateFormat + ".zip";
				String compressedDir = compressDirectory(zipPathWithName, path);
				// System.out.println(compressedDir);
				if (!compressedDir.equals("faillure")) {
					Map<String, Object> model = new HashMap<>();
					model.put("msg", request.getMsg());
					String[] errorsCompressDir = compressedDir.split("!");
					MailResponse response = new MailResponse();
					if (errorsCompressDir[0].equals("error")) {
						response = service.sendEmailZip(request, errorsCompressDir[1], zipName, model);

						response.setMessage("error files count");

					} else {
						response = service.sendEmailZip(request, compressedDir, zipName, model);

					}
					return response;
				}
			}
			return null;
		} catch (Exception e) {
			System.out.println("Exception while sending email zip==>" + e.getMessage());
		}
		return null;
	}

	// *************************************************sauvgarder dans la BD
	// l'email ***********************************************************
	@PostMapping({ "SaveSentEmail" })
	public EmailDB saveSentEmail(@RequestBody EmailDB email) {
		try {

			return emailDbRepo.save(email);

		} catch (Exception e) {
			System.out.println("Exception while saving sent email==>" + e.getMessage());
		}
		return null;

	}

	// *******************************suppression du zip aprés envoie
	// émail********************************************************
	@PostMapping({ "deleteZip" })
	public Structure deleteZip(@RequestBody Structure structure) {
		try {
			Folder folder = folderRepo.findByFolderName("ETAT");
			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;
			String zipPathWithName = folder.getFOLDERPATH() + "ETAT" + File.separator + currentYear + File.separator
					+ dateFormat + File.separator + structure.getSTRUCTURENAME() + " " + dateFormat + ".zip";
			Files.deleteIfExists(Paths.get(zipPathWithName));
			return structure;
		} catch (NoSuchFileException e) {
			System.out.println("No such file/directory exists deleteZip()==>" + e.getMessage());
		} catch (DirectoryNotEmptyException e) {
			System.out.println("Directory is not empty.deleteZip()==>" + e.getMessage());
		} catch (IOException e) {
			System.out.println("Invalid permissions. deleteZip()==>" + e.getMessage());

		} catch (Exception e) {
			System.out.println("Exception deleteZip()==>" + e.getMessage());
		}
		return null;

	}

	@GetMapping("api/auth/deleteAllSwap")
	public PayMonth deleteAllSwap() {

		try {
			File path = new File(SwapPath.toString());
			FileUtils.cleanDirectory(path);
			PayMonth currentDate = paymonthRepo.findByState();
			return currentDate;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception deleteAllSwap()==>" + e.getMessage());
		}
		return null;
	}

	// ***********************************************sauvgarder dans la
	// BDarchiveSentFiles***********************************************************
	@PostMapping({ "SaveArchiveSentFiles" })
	public List<ArchiveSentFiles> SaveArchiveSentFiles(@RequestBody List<ArchiveSentFiles> files) {
		try {
			for (int i = 0; i < files.size(); i++) {

				// System.out.println(files.get(i).getIdemail()+" "+files.get(i).getIdfile());
				archiveSentFilesRepo.save(files.get(i));
			}
			return files;
		} catch (Exception e) {
			System.out.println("Exception while saving ArchiveSentFiles==>" + e.getMessage());

		}

		return null;

	}

	@PostMapping({ "copyFileToPrint" })
	public List<FileToPrint> copyFileToPrint(@RequestBody Structure str) {
		try {

			List<FileToPrint> allFilesToPrint = fileToPrintRepo.findByStructure(str.getIDSTRUCTURE());
			List<Folder> folder = folderRepo.findByStatus(2);
			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;

			// ********************************folder generation if not exist
			String pathWithYear = folder.get(0).getFOLDERPATH() + folder.get(0).getFOLDERNAME() + File.separator
					+ currentYear;
			String pathWithMounth = pathWithYear + File.separator + dateFormat;
			Folder folderEtat = folderRepo.findByFolderName("ETAT");
			String pathEtat = folderEtat.getFOLDERPATH() + folderEtat.getFOLDERNAME() + File.separator + currentYear
					+ File.separator + dateFormat;
			File fileYear = new File(pathWithYear);
			if (!fileYear.exists()) {
				fileYear.mkdir();
			}
			File fileMounth = new File(pathWithMounth);
			if (!fileMounth.exists()) {
				fileMounth.mkdir();
			}

			for (int i = 0; i < allFilesToPrint.size(); i++) {
				Optional<Structure> structure = structureRepo.findById(allFilesToPrint.get(i).getIdStructure());
				Optional<FileType> file = fileTypeRepo.findById(allFilesToPrint.get(i).getIdFileType());
				if (structure.get() != null && file.get() != null) {
					String source = pathEtat + File.separator + structure.get().getSTRUCTURENAME() + " " + dateFormat
							+ File.separator + file.get().getPrefixfiletype() + " " + structure.get().getSTRUCTURENAME()
							+ " " + dateFormat + ".txt";
					String filePath = pathWithMounth + File.separator + file.get().getPrefixfiletype() + " "
							+ structure.get().getSTRUCTURENAME() + " " + dateFormat + ".txt";
					copyFile(source, pathWithMounth, filePath);
				}

			}

			return allFilesToPrint;
		} catch (Exception e) {
			System.out.println("Exception copyFileToPrint()==>" + e.getMessage());

		}

		return null;

	}

	// ***************************************get Efiles for choosed struture (find
	// search efiles by id structure)*******************************************
	@PostMapping({ "getEfiles" })
	public List<Efile> getEfiles(@RequestBody Structure structure) {
		try {
			List<Efile> files = efileRepo.findByIdStructure(structure.getIDSTRUCTURE());
			if (files != null) {
				return files;
			}
		} catch (Exception e) {
			System.out.println("Exception whilegetting Efiles{ getEfiles()}==>" + e.getMessage());
		}
		return null;
	}

	// ********************************************************** get All
	// structures*********************************************************************
	@GetMapping({ "/getAllStructures" })
	public List<Structure> getAllStructures() {
		List<Structure> lesStructures = new ArrayList();
		try {
			lesStructures = structureRepo.findByStatus(-1);

			return lesStructures;
		} catch (Exception e) {
			System.out.println("Exception getAllStructures()==>" + e.getMessage());
			lesStructures = null;
		}
		return lesStructures;
	}

	// **************************************************update structure
	// status***************************************
	@PostMapping({ "updateStructureStatus" })
	public Structure updateStructureStatus(@RequestBody Structure structure) {
		try {
			structureRepo.save(structure);
			return structure;
		} catch (Exception e) {
			System.out.println("Exception updateStructureStatus()==>" + e.getMessage());
		}
		return null;
	}

	// *********************************************Suspendre
	// Structure**********************************************************************************
	@PostMapping({ "suspendreStructure" })
	public Structure suspendreStructure(@RequestBody Structure structure) {
		try {
			structureRepo.save(structure);
			return structure;
		} catch (Exception e) {
			System.out.println("Exception suspendreStructure()==>" + e.getMessage());
		}
		return null;
	}

	// ************************************Réactivation de la
	// structure*********************************************************************************
	@PostMapping({ "activerStructure" })
	public Structure activerStructure(@RequestBody Structure structure) {
		try {
			structureRepo.save(structure);
			return structure;
		} catch (Exception e) {
			System.out.println("Exception activerStructure()==>" + e.getMessage());
		}
		return null;
	}

	// *****************************filter by codeLike and codeNotLike
	// structure****************************************

	/*
	 * public List<RubNum> filtrerFrubN(List<RubNum> data, Structure structure) {
	 * 
	 * String[] codeLike = structure.getSTRUCTURECODELIKE().split("/"); String[]
	 * codeNotLike = structure.getSTRUCTURECODENOTLIKE().split("/"); List<RubNum>
	 * filtredData = new ArrayList();
	 * 
	 * for (int i = 0; i < data.size(); i++) { for (int j = 0; j < codeLike.length;
	 * j++) {
	 * 
	 * if (data.get(i).getDIREC().startsWith(codeLike[j])) {
	 * 
	 * for (int k = 0; k < codeNotLike.length; k++) {
	 * 
	 * if (!data.get(i).getDIREC().startsWith(codeNotLike[k])) {
	 * 
	 * filtredData.add(data.get(i)); } }
	 * 
	 * } }
	 * 
	 * } return filtredData;
	 * 
	 * }
	 * 
	 * public List<RubAlph> filtrerFrubA(List<RubAlph> data, Structure structure) {
	 * 
	 * String[] codeLike = structure.getSTRUCTURECODELIKE().split("/"); String[]
	 * codeNotLike = structure.getSTRUCTURECODENOTLIKE().split("/"); List<RubAlph>
	 * filtredData = new ArrayList();
	 * 
	 * for (int i = 0; i < data.size(); i++) { for (int j = 0; j < codeLike.length;
	 * j++) { if (data.get(i).getDIREC().startsWith(codeLike[j])) { for (int k = 0;
	 * k < codeNotLike.length; k++) { if
	 * (!data.get(i).getDIREC().startsWith(codeNotLike[k])) {
	 * filtredData.add(data.get(i)); } }
	 * 
	 * } }
	 * 
	 * } return filtredData;
	 * 
	 * }
	 * 
	 * public List<Pers> filtrerPers(List<Pers> data, Structure structure) {
	 * 
	 * String[] codeLike = structure.getSTRUCTURECODELIKE().split("/"); String[]
	 * codeNotLike = structure.getSTRUCTURECODENOTLIKE().split("/"); List<Pers>
	 * filtredData = new ArrayList();
	 * 
	 * for (int i = 0; i < data.size(); i++) { for (int j = 0; j < codeLike.length;
	 * j++) { if (data.get(i).getDIV().startsWith(codeLike[j])) { for (int k = 0; k
	 * < codeNotLike.length; k++) { if
	 * (!data.get(i).getDIV().startsWith(codeNotLike[k])) {
	 * filtredData.add(data.get(i)); } }
	 * 
	 * } }
	 * 
	 * } return filtredData;
	 * 
	 * }
	 * 
	 * public List<NewPaie> filtrerNewPaie(List<NewPaie> data, Structure structure)
	 * {
	 * 
	 * String[] codeLike = structure.getSTRUCTURECODELIKE().split("/"); String[]
	 * codeNotLike = structure.getSTRUCTURECODENOTLIKE().split("/"); List<NewPaie>
	 * filtredData = new ArrayList();
	 * 
	 * for (int i = 0; i < data.size(); i++) { for (int j = 0; j < codeLike.length;
	 * j++) { if (data.get(i).getStr().startsWith(codeLike[j])) { for (int k = 0; k
	 * < codeNotLike.length; k++) { if
	 * (!data.get(i).getStr().startsWith(codeNotLike[k])) {
	 * filtredData.add(data.get(i)); } }
	 * 
	 * } }
	 * 
	 * } return filtredData;
	 * 
	 * }
	 */
	// ************************************generer les fichiers par
	// structure*********************************************************//
	// génération des fichiers FRUBN
	@PostMapping({ "generateFrubNStr" })
	public Structure generateFrubNStr(@RequestBody Structure structure) throws FileNotFoundException, JRException {

		try {

			SimpleJdbcCall simpleJdbcCall;
			List<RubNum> filtredFrubNum = new ArrayList();

			simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("FINDRUB");
			String codelike = "^(" + structure.getFICHIERCODELIKE() + ")";
			String codenotLike = "^(" + structure.getFICHIERCODENOTLIKE() + ")";
			SqlParameterSource in = new MapSqlParameterSource().addValue("frub", 1);
			((MapSqlParameterSource) in).addValue("conditionlike", codelike);

			((MapSqlParameterSource) in).addValue("conditionnotlike", codenotLike);
			Map out = simpleJdbcCall.execute(in);

			if (out != null) {

				filtredFrubNum = (ArrayList<RubNum>) out.get("XRESULT");
			}

			// **********************************************get current date from payMonth

			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;

			// ******************************************get Folder path
			Folder folder = folderRepo.findByFolderName("ETAT");
			Folder folder2 = folderRepo.findByFolderName("FRUB");
			// ********************************folder generation if not exist
			String pathWithYear = folder.getFOLDERPATH() + folder.getFOLDERNAME() + File.separator + currentYear;
			String pathWithMounth = pathWithYear + File.separator + dateFormat;

			String pathWithYear2 = folder2.getFOLDERPATH() + folder2.getFOLDERNAME() + File.separator + currentYear;
			String pathWithMounth2 = pathWithYear2 + File.separator + dateFormat;
			File fileYear = new File(pathWithYear);
			if (!fileYear.exists()) {
				fileYear.mkdir();
			}
			File fileMounth = new File(pathWithMounth);
			if (!fileMounth.exists()) {
				fileMounth.mkdir();
			}

			File fileYear2 = new File(pathWithYear2);
			if (!fileYear2.exists()) {
				fileYear2.mkdir();
			}
			File fileMounth2 = new File(pathWithMounth2);
			if (!fileMounth2.exists()) {
				fileMounth2.mkdir();
			}
			String pathWithStructure = pathWithMounth + File.separator + structure.getSTRUCTURENAME() + " "
					+ dateFormat;
			File fileStructure = new File(pathWithStructure);
			if (!fileStructure.exists()) {
				fileStructure.mkdir();
			}

			// load file and compile it
			File filerubN = ResourceUtils.getFile("classpath:rubNum.jrxml");
			JasperReport jasperReport13 = JasperCompileManager.compileReport(filerubN.getAbsolutePath());

			JRBeanCollectionDataSource dataSource13 = new JRBeanCollectionDataSource(filtredFrubNum);
			JRSwapFileVirtualizer virtualizer = null;
			virtualizer = new JRSwapFileVirtualizer(3, new JRSwapFile(SwapPath, 2048, 1024), true);
			Map<String, Object> param = new HashMap<>();
			param.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			JasperPrint jasperPrint13 = JasperFillManager.fillReport(jasperReport13, param, dataSource13);

			JRXlsxExporter exporter13 = new JRXlsxExporter();
			exporter13.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter13.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter13.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint13);
			Object outputFileName13 = pathWithStructure + File.separator + "FRUB" + " " + structure.getSTRUCTURENAME()
					+ " " + dateFormat + ".xlsx";
			exporter13.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName13);
			exporter13.exportReport();
			generatedToDBF("", outputFileName13.toString(), pathWithStructure);
			String pathfile = pathWithMounth2 + File.separator + "FRUB" + " " + structure.getSTRUCTURENAME() + " "
					+ dateFormat + ".xlsx";
			String pathfile2 = pathWithMounth2 + File.separator + "FRUB" + " " + structure.getSTRUCTURENAME() + " "
					+ dateFormat + ".dbf";
			String pathDbf = pathWithStructure + File.separator + "FRUB" + " " + structure.getSTRUCTURENAME() + " "
					+ dateFormat + ".dbf";
			//copyFile(outputFileName13.toString(), pathWithMounth2, pathfile);//pour copier frub.xlsx dans Dindrh
			copyFile(pathDbf, pathWithMounth2, pathfile2);//pour copier frub.dbf dans Dindrh
			virtualizer.cleanup();
			return structure;

		} catch (Exception e) {
			System.out.println(e.getMessage() + "==>generateFrubNStr() ");

		}
		return null;

	}

	public void copyFile(String source, String to, String filePath) {
		try {
			Path source1 = Paths.get(source);
			Path newdir = Paths.get(to);
			File target = new File(filePath);

			if (target.exists()) {
				if (target.delete()) {

					Files.copy(source1, newdir.resolve(source1.getFileName()));
				}
			} else {
				Files.copy(source1, newdir.resolve(source1.getFileName()));
			}
		} catch (IOException e) {
			System.out.println("exception copyFile==>" + e.getMessage());
		}

	}

	@PostMapping({ "generateFrubAStr" })
	public Structure generateFrubAStr(@RequestBody Structure structure) throws FileNotFoundException, JRException {

		try {
			SimpleJdbcCall simpleJdbcCall;
			List<RubAlph> filtredFrubT = new ArrayList();

			simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("FINDRUB");
			String codelike = "^(" + structure.getFICHIERCODELIKE() + ")";
			String codenotLike = "^(" + structure.getFICHIERCODENOTLIKE() + ")";
			SqlParameterSource in = new MapSqlParameterSource().addValue("frub", 0);
			((MapSqlParameterSource) in).addValue("conditionlike", codelike);

			((MapSqlParameterSource) in).addValue("conditionnotlike", codenotLike);
			Map out = simpleJdbcCall.execute(in);

			if (out != null) {

				filtredFrubT = (ArrayList<RubAlph>) out.get("XRESULT");
			}
			// **********************************************get current date from payMonth

			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;

			// ******************************************get Folder path
			Folder folder = folderRepo.findByFolderName("ETAT");
			Folder folder2 = folderRepo.findByFolderName("FRUB");
			// ********************************folder generation if not exist
			String pathWithYear = folder.getFOLDERPATH() + folder.getFOLDERNAME() + File.separator + currentYear;
			String pathWithMounth = pathWithYear + File.separator + dateFormat;

			String pathWithYear2 = folder2.getFOLDERPATH() + folder2.getFOLDERNAME() + File.separator + currentYear;
			String pathWithMounth2 = pathWithYear2 + File.separator + dateFormat;

			File fileYear = new File(pathWithYear);
			if (!fileYear.exists()) {
				fileYear.mkdir();
			}
			File fileMounth = new File(pathWithMounth);
			if (!fileMounth.exists()) {
				fileMounth.mkdir();
			}
			File fileYear2 = new File(pathWithYear2);
			if (!fileYear2.exists()) {
				fileYear2.mkdir();
			}
			File fileMounth2 = new File(pathWithMounth2);
			if (!fileMounth2.exists()) {
				fileMounth2.mkdir();
			}
			String pathWithStructure = pathWithMounth + File.separator + structure.getSTRUCTURENAME() + " "
					+ dateFormat;
			File fileStructure = new File(pathWithStructure);
			if (!fileStructure.exists()) {
				fileStructure.mkdir();
			}

			// load file and compile it

			File filerubA = ResourceUtils.getFile("classpath:rubAlph.jrxml");
			JasperReport jasperReport12 = JasperCompileManager.compileReport(filerubA.getAbsolutePath());
			JRBeanCollectionDataSource dataSource12 = new JRBeanCollectionDataSource(filtredFrubT);
			JRSwapFileVirtualizer virtualizer = null;
			virtualizer = new JRSwapFileVirtualizer(3, new JRSwapFile(SwapPath, 2048, 1024), true);
			Map<String, Object> param = new HashMap<>();
			param.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			JasperPrint jasperPrint12 = JasperFillManager.fillReport(jasperReport12, param, dataSource12);

			JRXlsxExporter exporter12 = new JRXlsxExporter();
			exporter12.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter12.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter12.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint12);
			Object outputFileName12 = pathWithStructure + File.separator + "FRUB" + " " + structure.getSTRUCTURENAME()
					+ " " + dateFormat + ".xlsx";
			exporter12.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName12);
			exporter12.exportReport();
			generatedToDBF("", outputFileName12.toString(), pathWithStructure);
			String pathfile = pathWithMounth2 + File.separator + "FRUB" + " " + structure.getSTRUCTURENAME() + " "
					+ dateFormat + ".xlsx";
			String pathfile2 = pathWithMounth2 + File.separator + "FRUB" + " " + structure.getSTRUCTURENAME() + " "
					+ dateFormat + ".dbf";
			String pathDbf = pathWithStructure + File.separator + "FRUB" + " " + structure.getSTRUCTURENAME() + " "
					+ dateFormat + ".dbf";
			//copyFile(outputFileName12.toString(), pathWithMounth2, pathfile); //pour copier frub.xlsx dans Dindrh
			copyFile(pathDbf, pathWithMounth2, pathfile2);//pour copier frub.dbf dans Dindrh
			virtualizer.cleanup();
			return structure;

		} catch (Exception e) {
			System.out.println(e.getMessage() + "==>generateFrubAStr() ");

		}
		return null;

	}

	// génération des fichiers NewPaie
	@PostMapping({ "generateNewPaieStr" })
	public Structure generateNewPaieStr(@RequestBody Structure structure) throws FileNotFoundException, JRException {

		try {

			SimpleJdbcCall simpleJdbcCall;
			List<NewPaie> filtredNewPaie = new ArrayList();

			simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("FINDNEWPAIE");
			String codelike = "^(" + structure.getFICHIERCODELIKE() + ")";
			String codenotLike = "^(" + structure.getFICHIERCODENOTLIKE() + ")";
			SqlParameterSource in = new MapSqlParameterSource().addValue("conditionlike", codelike);

			((MapSqlParameterSource) in).addValue("conditionnotlike", codenotLike);
			Map out = simpleJdbcCall.execute(in);

			if (out != null) {

				filtredNewPaie = (ArrayList<NewPaie>) out.get("XRESULT");
				// System.out.println(simpleJdbcCall.getCallString()+" here");
			}
			// **********************************************get current date from payMonth

			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;

			// ******************************************get Folder path
			Folder folder = folderRepo.findByFolderName("ETAT");
			// ********************************folder generation if not exist
			String pathWithYear = folder.getFOLDERPATH() + folder.getFOLDERNAME() + File.separator + currentYear;
			String pathWithMounth = pathWithYear + File.separator + dateFormat;

			File fileYear = new File(pathWithYear);
			if (!fileYear.exists()) {
				fileYear.mkdir();
			}
			File fileMounth = new File(pathWithMounth);
			if (!fileMounth.exists()) {
				fileMounth.mkdir();
			}
			String pathWithStructure = pathWithMounth + File.separator + structure.getSTRUCTURENAME() + " "
					+ dateFormat;
			File fileStructure = new File(pathWithStructure);
			if (!fileStructure.exists()) {
				fileStructure.mkdir();
			}
			File fileNewpaie = ResourceUtils.getFile("classpath:newPaie.jrxml");
			JasperReport jasperReport10 = JasperCompileManager.compileReport(fileNewpaie.getAbsolutePath());
			JRBeanCollectionDataSource dataSource10 = new JRBeanCollectionDataSource(filtredNewPaie);
			JRSwapFileVirtualizer virtualizer = null;
			virtualizer = new JRSwapFileVirtualizer(3, new JRSwapFile(SwapPath, 2048, 1024), true);
			Map<String, Object> param = new HashMap<>();
			param.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			JasperPrint jasperPrint10 = JasperFillManager.fillReport(jasperReport10, param, dataSource10);

			JRXlsxExporter exporter10 = new JRXlsxExporter();
			exporter10.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter10.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter10.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint10);
			Object outputFileName10 = pathWithStructure + File.separator + "NEWPAIE" + " "
					+ structure.getSTRUCTURENAME() + " " + dateFormat + ".xlsx";
			exporter10.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName10);
			exporter10.exportReport();
			generatedToDBF("", outputFileName10.toString(), pathWithStructure);
			virtualizer.cleanup();
			return structure;
		} catch (Exception e) {
			System.out.println(e.getMessage() + "==>generateNewPaieStr()");

		}
		return null;

	}

	// génération des fichiers Pers
	@PostMapping({ "generatePersStr" })
	public Structure generatePersStr(@RequestBody Structure structure) throws FileNotFoundException, JRException {

		try {

			SimpleJdbcCall simpleJdbcCall;
			List<Pers> filtredPers = new ArrayList();

			simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("FINDPERS");
			String codelike = "^(" + structure.getFICHIERCODELIKE() + ")";
			String codenotLike = "^(" + structure.getFICHIERCODENOTLIKE() + ")";
			SqlParameterSource in = new MapSqlParameterSource().addValue("conditionlike", codelike);

			((MapSqlParameterSource) in).addValue("conditionnotlike", codenotLike);
			Map out = simpleJdbcCall.execute(in);

			if (out != null) {

				filtredPers = (ArrayList<Pers>) out.get("XRESULT");
			}
			// **********************************************get current date from payMonth

			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;

			// ******************************************get Folder path
			Folder folder = folderRepo.findByFolderName("ETAT");
			// ********************************folder generation if not exist
			String pathWithYear = folder.getFOLDERPATH() + folder.getFOLDERNAME() + File.separator + currentYear;
			String pathWithMounth = pathWithYear + File.separator + dateFormat;

			File fileYear = new File(pathWithYear);
			if (!fileYear.exists()) {
				fileYear.mkdir();
			}
			File fileMounth = new File(pathWithMounth);
			if (!fileMounth.exists()) {
				fileMounth.mkdir();
			}
			String pathWithStructure = pathWithMounth + File.separator + structure.getSTRUCTURENAME() + " "
					+ dateFormat;
			File fileStructure = new File(pathWithStructure);
			if (!fileStructure.exists()) {
				fileStructure.mkdir();
			}
			// System.out.println(pathWithMounth);
			// load file and compile it
			File filePers = ResourceUtils.getFile("classpath:pers.jrxml");
			JasperReport jasperReport11 = JasperCompileManager.compileReport(filePers.getAbsolutePath());
			JRBeanCollectionDataSource dataSource11 = new JRBeanCollectionDataSource(filtredPers);
			JRSwapFileVirtualizer virtualizer = null;
			virtualizer = new JRSwapFileVirtualizer(3, new JRSwapFile(SwapPath, 2048, 1024), true);
			Map<String, Object> param = new HashMap<>();
			param.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			JasperPrint jasperPrint11 = JasperFillManager.fillReport(jasperReport11, param, dataSource11);
			JRXlsxExporter exporter11 = new JRXlsxExporter();
			// JRCsvExporter exporter = new JRCsvExporter();
			exporter11.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter11.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter11.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint11);
			Object outputFileName11 = pathWithStructure + File.separator + "PERS" + " " + structure.getSTRUCTURENAME()
					+ " " + dateFormat + ".xlsx";
			exporter11.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName11);

			exporter11.exportReport();
			generatedToDBF("", outputFileName11.toString(), pathWithStructure);

			virtualizer.cleanup();
			return structure;
		} catch (Exception e) {
			System.out.println(e.getMessage() + "==>generatePersStr()");

		}
		return null;

	}

	// ********************************Activer/suspendre
	// Tout********************************************************************************
	@GetMapping({ "suspendreAll" })
	public List<Structure> suspendreAll() {
		try {
			List<Structure> structures = structureRepo.findAll();
			for (int i = 0; i < structures.size(); i++) {
				structures.get(i).setIsactif(0);
				structureRepo.save(structures.get(i));
			}
			return structures;
		} catch (Exception e) {
			System.out.println(e.getMessage() + "==>suspendreAll() ");
		}
		return null;
	}

	@GetMapping({ "activerAll" })
	public List<Structure> activerAll() {
		try {
			List<Structure> structures = structureRepo.findAll();
			for (int i = 0; i < structures.size(); i++) {
				structures.get(i).setIsactif(1);
				structureRepo.save(structures.get(i));
			}
			return structures;
		} catch (Exception e) {
			System.out.println(e.getMessage() + "==>suspendreAll() ");
		}
		return null;
	}

	// *********************************************Récuperer les etat paie(filtrage
	// par mois et annee)**********************************************************
	@GetMapping({ "getEtatJournal" })
	public List<EtatJournal> getEtatJournal() {
		try {
			PayMonth currentPaymonth = paymonthRepo.findByState();
			String currentYear = currentPaymonth.getPaymonth().substring(0, 4);
			String currentMonth = currentPaymonth.getPaymonth().substring(4, 6);
			// return etatJournalRepo.findByPayMonth(currentMonth, currentYear);
			return etatJournalRepo.findByPayMonth();
		} catch (Exception e) {
			System.out.println("Exception getEtatJournal()==>" + e.getMessage());
		}
		return null;
	}

	@GetMapping({ "getEtatMand" })
	public List<EtatMand> getEtatMand() {
		try {
			PayMonth currentPaymonth = paymonthRepo.findByState();
			String currentYear = currentPaymonth.getPaymonth().substring(0, 4);
			String currentMonth = currentPaymonth.getPaymonth().substring(4, 6);
			String date = "01" + "/" + currentMonth + "/" + currentYear;
			// String date = "01/04/2016";
			Date sysDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
			// System.out.println(sysDate);
			return etatMandRepo.findByPayMonth();
		} catch (Exception e) {
			System.out.println("Exception getEtatMand()==>" + e.getMessage());
		}
		return null;
	}

	@GetMapping({ "getEtatMip" })
	public List<EtatMip> getEtatMip() {
		try {
			PayMonth currentPaymonth = paymonthRepo.findByState();
			String currentYear = currentPaymonth.getPaymonth().substring(0, 4);
			String currentMonth = currentPaymonth.getPaymonth().substring(4, 6);
			// return etatMipRepo.findByPayMonth(currentMonth, currentYear);
			return etatMipRepo.findByPayMonth();
		} catch (Exception e) {
			System.out.println("Exception getEtatMip()==>" + e.getMessage());
		}
		return null;
	}

	@GetMapping({ "getEtatRecap" })
	public List<EtatRecap> getEtatRecap() {
		try {
			PayMonth currentPaymonth = paymonthRepo.findByState();
			String currentYear = currentPaymonth.getPaymonth().substring(0, 4);
			String currentMonth = currentPaymonth.getPaymonth().substring(4, 6);
			String date = "01" + "/" + currentMonth + "/" + currentYear;
			// String date = "01/04/2016";
			Date sysDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
			// System.out.println(sysDate);
			return etatRecapRepo.findByPayMonth();
		} catch (Exception e) {
			System.out.println("Exception getEtatRecap()==>" + e.getMessage());
		}
		return null;
	}

	@GetMapping({ "getEtatRet" })
	public List<EtatRet> getEtatRet() {
		try {
			PayMonth currentPaymonth = paymonthRepo.findByState();
			String currentYear = currentPaymonth.getPaymonth().substring(0, 4);
			String currentMonth = currentPaymonth.getPaymonth().substring(4, 6);
			return etatRetRepo.findByPayMonth();
		} catch (Exception e) {
			System.out.println("Exception getEtatRet()==>" + e.getMessage());
		}
		return null;
	}

	// *********************************************Générer les etat paie(filtrage
	// par code structure)**********************************************************
	@PostMapping({ "generateJournal" })
	public List<EtatJournal> generateJournal(@RequestBody List<EtatJournal> journal, @RequestParam String structure) {
		try {
			// **********************************************get current date from payMonth

			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;

			// ******************************************get Folder path
			Folder folder = folderRepo.findByFolderName("ETAT");
			// ********************************folder generation if not exist
			String pathWithYear = folder.getFOLDERPATH() + folder.getFOLDERNAME() + File.separator + currentYear;
			String pathWithMounth = pathWithYear + File.separator + dateFormat;

			File fileYear = new File(pathWithYear);
			if (!fileYear.exists()) {
				fileYear.mkdir();
			}
			File fileMounth = new File(pathWithMounth);
			if (!fileMounth.exists()) {
				fileMounth.mkdir();
			}
			String pathWithStructure = pathWithMounth + File.separator + structure + " " + dateFormat;
			File fileStructure = new File(pathWithStructure);
			if (!fileStructure.exists()) {
				fileStructure.mkdir();
			}

			// load file and compile it
			File file = ResourceUtils.getFile("classpath:etatJour.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(journal);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

			JRTextExporter exporter = new JRTextExporter();
			exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, 180);
			exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 50);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			Object outputFileName = pathWithStructure + File.separator + "JOUR" + " " + structure + " " + dateFormat
					+ ".txt";
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName);
			exporter.exportReport();
			return journal;
		} catch (Exception e) {
			System.out.println("Exception generateJournal()==>" + e.getMessage());
		}
		return null;
	}

	@PostMapping({ "generateMip" })
	public List<EtatMip> generateMip(@RequestBody List<EtatMip> mip, @RequestParam String structure) {
		try {
			// **********************************************get current date from payMonth

			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;

			// ******************************************get Folder path
			Folder folder = folderRepo.findByFolderName("ETAT");
			// ********************************folder generation if not exist
			String pathWithYear = folder.getFOLDERPATH() + folder.getFOLDERNAME() + File.separator + currentYear;
			String pathWithMounth = pathWithYear + File.separator + dateFormat;
			File fileYear = new File(pathWithYear);
			if (!fileYear.exists()) {
				fileYear.mkdir();
			}
			File fileMounth = new File(pathWithMounth);
			if (!fileMounth.exists()) {
				fileMounth.mkdir();
			}
			String pathWithStructure = pathWithMounth + File.separator + structure + " " + dateFormat;
			File fileStructure = new File(pathWithStructure);
			if (!fileStructure.exists()) {
				fileStructure.mkdir();
			}

			// load file and compile it
			File file = ResourceUtils.getFile("classpath:etatMip.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(mip);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
			JRTextExporter exporter = new JRTextExporter();
			exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, 180);
			exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 50);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			Object outputFileName = pathWithStructure + File.separator + "MIP" + " " + structure.toString() + " "
					+ dateFormat + ".txt";
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName);

			exporter.exportReport();
			return mip;
		} catch (Exception e) {
			System.out.println("Exception generateMip()==>" + e.getMessage());
		}
		return null;
	}

	@PostMapping({ "generateRecap" })
	public List<EtatRecap> generateRecap(@RequestBody List<EtatRecap> recap, @RequestParam String structure) {
		try {
			// **********************************************get current date from payMonth

			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;

			// ******************************************get Folder path
			Folder folder = folderRepo.findByFolderName("ETAT");
			// ********************************folder generation if not exist
			String pathWithYear = folder.getFOLDERPATH() + folder.getFOLDERNAME() + File.separator + currentYear;
			String pathWithMounth = pathWithYear + File.separator + dateFormat;
			File fileYear = new File(pathWithYear);
			if (!fileYear.exists()) {
				fileYear.mkdir();
			}
			File fileMounth = new File(pathWithMounth);
			if (!fileMounth.exists()) {
				fileMounth.mkdir();
			}
			String pathWithStructure = pathWithMounth + File.separator + structure + " " + dateFormat;
			File fileStructure = new File(pathWithStructure);
			if (!fileStructure.exists()) {
				fileStructure.mkdir();
			}
			List<EtatRecap> mainReportData = new ArrayList();
			List<EtatRecap> mainReportDataTotaux = new ArrayList();// first subreport (calcul base cotisation totaux
																	// analytique)
			List<EtatRecap> subReportMonthData = new ArrayList();
			List<EtatRecap> subReportMonthCssData = new ArrayList();
			List<EtatRecap> subReportDirData = new ArrayList();
			List<EtatRecap> subReportDirCssData = new ArrayList();
			List<EtatRecap> subReportDivData = new ArrayList();
			List<EtatRecap> subReportDivCssData = new ArrayList();
			for (int i = 0; i < recap.size(); i++) {
				if (recap.get(i).getReport().equals("0")) {
					mainReportData.add(recap.get(i));
					subReportDivData.add(recap.get(i));
				}
				if (recap.get(i).getReport().equals("1")) {
					/*
					 * EtatRecap1 recap1 = new EtatRecap1();
					 * recap1.setAgtcptanal1(recap.get(i).getAgtcptanal());
					 * recap1.setBulmoispaie1(recap.get(i).getBulmoispaie());
					 * recap1.setCss1(recap.get(i).getCss());
					 * recap1.setDbulcrub1(recap.get(i).getDbulcrub());
					 * recap1.setDbuldesignrub1(recap.get(i).getDbuldesignrub());
					 * recap1.setDbulimp1(recap.get(i).getDbulimp());
					 * recap1.setDbulnature1(recap.get(i).getDbulnature());
					 * recap1.setDir1(recap.get(i).getDir());
					 * recap1.setDirdes1(recap.get(i).getDirdes());
					 * recap1.setDiv1(recap.get(i).getDiv());
					 * recap1.setDivdes1(recap.get(i).getDivdes());
					 * recap1.setMtbase1(recap.get(i).getMtbase());
					 * recap1.setMtrub1(recap.get(i).getMtrub());
					 * recap1.setReport1(recap.get(i).getReport());
					 * mainReportDataTotaux.add(recap1);
					 */
					mainReportDataTotaux.add(recap.get(i));
					subReportDivCssData.add(recap.get(i));

				}
				if (recap.get(i).getReport().equals("2")) {
					subReportMonthData.add(recap.get(i));
				}
				if (recap.get(i).getReport().equals("3")) {
					subReportMonthCssData.add(recap.get(i));

				}
				if (recap.get(i).getReport().equals("4")) {
					subReportDirData.add(recap.get(i));

				}
				if (recap.get(i).getReport().equals("5")) {
					subReportDirCssData.add(recap.get(i));

				}
				/*
				 * if(recap.get(i).getReport().equals("6")) {
				 * subReportDivData.add(recap.get(i));
				 * 
				 * } if(recap.get(i).getReport().equals("7")) {
				 * subReportDivCssData.add(recap.get(i));
				 * 
				 * }
				 */

			}

			// transform div data without css and sort by div
			List<EtatRecap> transform = subReportDivData.stream()
					.collect(Collectors.groupingBy(foo -> getGroupingByKey(foo))).entrySet().stream()
					.map(e -> e.getValue().stream()
							.reduce((f1, f2) -> new EtatRecap(f1.getDiv(), f1.getDir(), f1.getBulmoispaie(),
									f1.getAgtcptanal(), f1.getDbulcrub(), f1.getDbulrappel(), f1.getDbuldesignrub(),
									f1.getDbulnature(), f1.getDbulimp(), f1.getMtbase().add(f2.getMtbase()),
									f1.getMtrub().add(f2.getMtrub()), f1.getDivdes(), f1.getDirdes(), f1.getCss(),
									f1.getReport())))
					.map(f -> f.get()).collect(Collectors.toList());

			java.util.Collections.sort(transform, new DivSorter());
			// transform div data with css and sort by div
			List<EtatRecap> transform2 = subReportDivCssData.stream()
					.collect(Collectors.groupingBy(foo -> getGroupingByKey3(foo))).entrySet().stream()
					.map(e -> e.getValue().stream()
							.reduce((f1, f2) -> new EtatRecap(f1.getDiv(), f1.getDir(), f1.getBulmoispaie(),
									f1.getAgtcptanal(), f1.getDbulcrub(), f1.getDbulrappel(), f1.getDbuldesignrub(),
									f1.getDbulnature(), f1.getDbulimp(), f1.getMtbase().add(f2.getMtbase()),
									f1.getMtrub().add(f2.getMtrub()), f1.getDivdes(), f1.getDirdes(), f1.getCss(),
									f1.getReport())))
					.map(f -> f.get()).collect(Collectors.toList());

			java.util.Collections.sort(transform2, new DivSorter());

			// transform entreprise data without css and sort by crub
			List<EtatRecap> entreprise = subReportDivData.stream()
					.collect(Collectors.groupingBy(foo -> getGroupingByKey2(foo))).entrySet().stream()
					.map(e -> e.getValue().stream()
							.reduce((f1, f2) -> new EtatRecap(f1.getDiv(), f1.getDir(), f1.getBulmoispaie(),
									f1.getAgtcptanal(), f1.getDbulcrub(), f1.getDbulrappel(), f1.getDbuldesignrub(),
									f1.getDbulnature(), f1.getDbulimp(), f1.getMtbase().add(f2.getMtbase()),
									f1.getMtrub().add(f2.getMtrub()), f1.getDivdes(), f1.getDirdes(), f1.getCss(),
									f1.getReport())))
					.map(f -> f.get()).collect(Collectors.toList());

			java.util.Collections.sort(entreprise, new DbulcrubSorter());
			// j'ai pas besoin d'utiliser le group by jasper s'occupe de calculer les sommes
			/*
			 * List<EtatRecap> entrepriseCss = subReportDivCssData.stream().collect(
			 * Collectors.groupingBy(foo ->getGroupingByKey3(foo))) .entrySet().stream()
			 * .map(e -> e.getValue().stream() .reduce((f1,f2) -> new
			 * EtatRecap(f1.getDiv(),f1.getDir(),f1.getBulmoispaie(),f1.getAgtcptanal(),f1.
			 * getDbulcrub(),f1.getDbulrappel(),f1.getDbuldesignrub(),
			 * f1.getDbulnature(),f1.getDbulimp(),f1.getMtbase().add(f2.getMtbase()),f1.
			 * getMtrub().add(f2.getMtrub())
			 * ,f1.getDivdes(),f1.getDirdes(),f1.getCss(),f1.getReport() ))) .map(f ->
			 * f.get()) .collect(Collectors.toList());
			 * 
			 * java.util.Collections.sort(entrepriseCss,new DbulcrubSorter() ); //transform
			 * entreprise data with css and sort by div
			 * 
			 * System.out.println(entrepriseCss.size()); for(int
			 * i=0;i<entrepriseCss.size();i++) {
			 * System.out.println(entrepriseCss.get(i).getDbulcrub()+"         "
			 * +entrepriseCss.get(i).getCss()+"      "+entrepriseCss.get(i).getMtbase()
			 * +"           "+entrepriseCss.get(i).getMtrub()); }
			 * //System.out.println(subReportDivCssData.size());
			 */

			// load file and compile it
			// get path files
			File file = ResourceUtils.getFile("classpath:etatRecap.jrxml");
			File file2 = ResourceUtils.getFile("classpath:subReport.jrxml");

			File fileMonth = ResourceUtils.getFile("classpath:subReportMonth.jrxml");
			File fileMonthCss = ResourceUtils.getFile("classpath:subReportMonthCss.jrxml");
			File fileDir = ResourceUtils.getFile("classpath:subReportDir.jrxml");
			File fileDirCss = ResourceUtils.getFile("classpath:subReportDirCss.jrxml");
			File fileDiv = ResourceUtils.getFile("classpath:subReportDiv.jrxml");
			File fileDivCss = ResourceUtils.getFile("classpath:subReportDivCss.jrxml");
			File fileEntreprise = ResourceUtils.getFile("classpath:subReportEntreprise.jrxml");
			File fileEntrepriseCss = ResourceUtils.getFile("classpath:subReportEntrepriseCss.jrxml");

			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JasperReport subReport = JasperCompileManager.compileReport(file2.getAbsolutePath());

			JasperReport subReportMonth = JasperCompileManager.compileReport(fileMonth.getAbsolutePath());
			JasperReport subReportMonthCss = JasperCompileManager.compileReport(fileMonthCss.getAbsolutePath());
			JasperReport subReportDir = JasperCompileManager.compileReport(fileDir.getAbsolutePath());
			JasperReport subReportDirCss = JasperCompileManager.compileReport(fileDirCss.getAbsolutePath());
			JasperReport subReportDiv = JasperCompileManager.compileReport(fileDiv.getAbsolutePath());
			JasperReport subReportDivCss = JasperCompileManager.compileReport(fileDivCss.getAbsolutePath());
			JasperReport subReportEntreprise = JasperCompileManager.compileReport(fileEntreprise.getAbsolutePath());
			JasperReport subReportEntrepriseCss = JasperCompileManager
					.compileReport(fileEntrepriseCss.getAbsolutePath());

			// get data source for files
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(mainReportData);

			JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(mainReportDataTotaux);
			JRBeanCollectionDataSource dataSourceMonth = new JRBeanCollectionDataSource(subReportMonthData);
			JRBeanCollectionDataSource dataSourceMonthCss = new JRBeanCollectionDataSource(subReportMonthCssData);
			JRBeanCollectionDataSource dataSourceDir = new JRBeanCollectionDataSource(subReportDirData);
			JRBeanCollectionDataSource dataSourceDirCss = new JRBeanCollectionDataSource(subReportDirCssData);
			JRBeanCollectionDataSource dataSourceDiv = new JRBeanCollectionDataSource(transform);
			JRBeanCollectionDataSource dataSourceDivCss = new JRBeanCollectionDataSource(transform2);
			JRBeanCollectionDataSource dataSourceEntreprise = new JRBeanCollectionDataSource(entreprise);
			// JRBeanCollectionDataSource dataSourceEntrepriseCss = new
			// JRBeanCollectionDataSource(entrepriseCss);
			JRBeanCollectionDataSource dataSourceEntrepriseCss = new JRBeanCollectionDataSource(subReportDivCssData);

			// parameters to send
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("subReport", subReport);
			parameters.put("totaux1", dataSource2);

			parameters.put("subReportMonth", subReportMonth);
			parameters.put("dataSourceMonth", dataSourceMonth);
			parameters.put("subReportMonthCss", subReportMonthCss);
			parameters.put("dataSourceMonthCss", dataSourceMonthCss);
			parameters.put("subReportDir", subReportDir);
			parameters.put("dataSourceDir", dataSourceDir);
			parameters.put("subReportDirCss", subReportDirCss);
			parameters.put("dataSourceDirCss", dataSourceDirCss);
			parameters.put("subReportDivCss", subReportDivCss);
			parameters.put("dataSourceDivCss", dataSourceDivCss);
			parameters.put("subReportDiv", subReportDiv);
			parameters.put("dataSourceDiv", dataSourceDiv);
			parameters.put("subReportEntreprise", subReportEntreprise);
			parameters.put("dataSourceEntreprise", dataSourceEntreprise);
			parameters.put("subReportEntrepriseCss", subReportEntrepriseCss);
			parameters.put("dataSourceEntrepriseCss", dataSourceEntrepriseCss);
			// send current month as parameter
			String date = "01" + "/" + currentMonth + "/" + currentYear;
			Date sysDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
			parameters.put("currenntMonth", sysDate);

			// fill main report and sub reports by sending data with parameter and export
			// the report
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
			JRTextExporter exporter = new JRTextExporter();
			exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, 180);
			exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 50);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			Object outputFileName = pathWithStructure + File.separator + "RECAP" + " " + structure.toString() + " "
					+ dateFormat + ".txt";
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName);

			exporter.exportReport();
			return recap;
		} catch (Exception e) {
			System.out.println("Exception generateRecap()==>" + e.getMessage());
		}
		return null;
	}

	private String getGroupingByKey(EtatRecap p) {
		return p.getDiv() + p.getDbulcrub() + p.getDbulrappel();
	}

	private String getGroupingByKey2(EtatRecap p) {
		return p.getDbulcrub() + p.getDbulrappel();
	}

	private String getGroupingByKey3(EtatRecap p) {
		return p.getDiv() + p.getDbulcrub() + p.getDbulrappel() + p.getCss();
	}

	@PostMapping({ "generateRet" })
	public List<EtatRet> generateRet(@RequestBody List<EtatRet> ret, @RequestParam String structure) {
		try {
			// **********************************************get current date from payMonth

			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;

			// ******************************************get Folder path
			Folder folder = folderRepo.findByFolderName("ETAT");
			// ********************************folder generation if not exist
			String pathWithYear = folder.getFOLDERPATH() + folder.getFOLDERNAME() + File.separator + currentYear;
			String pathWithMounth = pathWithYear + File.separator + dateFormat;
			File fileYear = new File(pathWithYear);
			if (!fileYear.exists()) {
				fileYear.mkdir();
			}
			File fileMounth = new File(pathWithMounth);
			if (!fileMounth.exists()) {
				fileMounth.mkdir();
			}
			String pathWithStructure = pathWithMounth + File.separator + structure + " " + dateFormat;
			File fileStructure = new File(pathWithStructure);
			if (!fileStructure.exists()) {
				fileStructure.mkdir();
			}

			// load file and compile it
			File file = ResourceUtils.getFile("classpath:etatRet.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ret);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
			JRTextExporter exporter = new JRTextExporter();
			exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, 180);
			exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 50);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			Object outputFileName = pathWithStructure + File.separator + "RET" + " " + structure.toString() + " "
					+ dateFormat + ".txt";
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName);

			exporter.exportReport();
			return ret;
		} catch (Exception e) {
			System.out.println("Exception generateRet()==>" + e.getMessage());
		}
		return null;
	}

	@PostMapping({ "generateMand" })
	public List<EtatMand> generateMand(@RequestBody List<EtatMand> mand, @RequestParam String structure) {
		try {
			// **********************************************get current date from payMonth

			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;
			String date = "01" + "/" + currentMonth + "/" + currentYear;
			// String date = "01/04/2016";// for test
			Date sysDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
			// ******************************************get Folder path
			Folder folder = folderRepo.findByFolderName("ETAT");
			// ********************************folder generation if not exist
			String pathWithYear = folder.getFOLDERPATH() + folder.getFOLDERNAME() + File.separator + currentYear;
			String pathWithMounth = pathWithYear + File.separator + dateFormat;
			File fileYear = new File(pathWithYear);
			if (!fileYear.exists()) {
				fileYear.mkdir();
			}
			File fileMounth = new File(pathWithMounth);
			if (!fileMounth.exists()) {
				fileMounth.mkdir();
			}
			String pathWithStructure = pathWithMounth + File.separator + structure + " " + dateFormat;
			File fileStructure = new File(pathWithStructure);
			if (!fileStructure.exists()) {
				fileStructure.mkdir();
			}
			/*
			 * for(int i=0;i<mand.size();i++) { if(mand.get(i).getBulnet()==new
			 * BigDecimal(0)) { mand.get(i).setBulnet(new BigDecimal(0)); } }
			 */

			// load file and compile it
			File file = ResourceUtils.getFile("classpath:etatMand.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

			JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(mand);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("currenntMonth", sysDate);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, data);

			JRTextExporter exporter = new JRTextExporter();

			exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, 180);

			exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 50);

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

			Object outputFileName = pathWithStructure + File.separator + "MAND" + " " + structure.toString() + " "
					+ dateFormat + ".txt";
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName);

			exporter.exportReport();

			return mand;
		} catch (Exception e) {
			System.out.println("Exception generateMand()==>" + e.getMessage());
		}
		return null;
	}

	@PostMapping({ "saveGeneratedFiles" })
	public List<Efile> saveGeneratedFiles(@RequestBody List<Efile> files) {
		try {
			Integer idFile = 0;
			// **********************************************get current date from payMonth
			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;

			for (int i = 0; i < files.size(); i++) {
				// System.out.println(files.get(i).getFilename());
				idFile = fileTypeRepo.findByPrefixFile(files.get(i).getFilename());
				Optional<Structure> structure = structureRepo.findById(files.get(i).getIdstructure());
				// System.out.println(idFile);

				if (idFile != null && structure.get() != null) {
					files.get(i).setIdfiletype(idFile);
					files.get(i).setIdpaymonth(currentDate.getId());
					files.get(i).setFilename(
							files.get(i).getFilename() + " " + structure.get().getSTRUCTURENAME() + " " + dateFormat);
					// efileRepo.save(files.get(i));
				}
			}

			for (int j = 0; j < files.size(); j++) {
				Efile file = efileRepo.findByIdStrIdFile(files.get(j).getIdstructure(), files.get(j).getIdfiletype());
				Optional<Structure> structure = structureRepo.findById(files.get(j).getIdstructure());
				if (file != null) {
					file.setFilegenerationdate(files.get(j).getFilegenerationdate());
					file.setIduser(files.get(j).getIduser());
					String[] fileName = files.get(j).getFilename().split(" ");
					file.setFilename(fileName[0] + " " + structure.get().getSTRUCTURENAME() + " " + dateFormat);
					file.setIdpaymonth(files.get(j).getIdpaymonth());
					efileRepo.save(file);
				} else {
					efileRepo.save(files.get(j));
				}
			}
			return files;
		} catch (Exception e) {
			System.out.println("Exception saveGeneratedFiles()==>" + e.getMessage());
		}
		return null;
	}

	@PostMapping({ "updateStructureFilesGenerated" })
	public Structure updateStructureFilesGenerated(@RequestBody Structure structure) {
		try {

			structureRepo.save(structure);
			return structure;
		} catch (Exception e) {
			System.out.println("Exception updateStructureFilesGenerated()==>" + e.getMessage());
		}
		return null;
	}

	/****************************************************************
	 * Cloture Mois
	 *****************************************************************************************************/
	// confirmation cloture mois (update paymonth)
	@GetMapping({ "/updatePayMonth" })
	public PayMonth updatePayMonth() {
		try {
			PayMonth currentPaymonth = paymonthRepo.findByState();
			currentPaymonth.setState(0);
			paymonthRepo.save(currentPaymonth);
			String currentYear = currentPaymonth.getPaymonth().substring(0, 4);
			String currentMonth = currentPaymonth.getPaymonth().substring(4, 6);
			// get next month
			Calendar calendar = new GregorianCalendar(Integer.valueOf(currentYear), Integer.valueOf(currentMonth) - 1,
					1, 23, 30, 0);

			calendar.add(Calendar.MONTH, 1);
			Date nextMonth = calendar.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");

			PayMonth nextPaymonth = paymonthRepo.findByPaymonth(formatter.format(nextMonth));
			nextPaymonth.setState(1);
			paymonthRepo.save(nextPaymonth);
			return nextPaymonth;
		} catch (Exception e) {
			System.out.println("Exception  updatePayMonth()==>" + e.getMessage());
		}

		return null;
	}

	// update structure status ==3
	@GetMapping({ "/updateStructureClotureMois" })
	public List<Structure> updateStructureClotureMois() {
		try {
			List<Structure> structures = structureRepo.findAll();
			for (int i = 0; i < structures.size(); i++) {
				structures.get(i).setSTATUSSTRUCTURE(3);
				structures.get(i).setFlagetat(0);
				structures.get(i).setFlagfichier(0);
				structures.get(i).setIsactif(1);
				structureRepo.save(structures.get(i));
			}
			return structures;
		} catch (Exception e) {
			System.out.println("Exception updateStructureClotureMois()====>" + e.getMessage());
		}
		return null;
	}

	/**************************************************************
	 * Cloture paie
	 ***************************************************************************************/

	// get All folders pour la génération des fichiers
	@GetMapping({ "/getAllFolders" })
	public List<Folder> getallFolders() {
		List<Folder> folders = new ArrayList();
		try {
			folders = folderRepo.findByStatus(1);
			return folders;
		} catch (Exception e) {
			System.out.println("Exception getallFolders()==>" + e.getMessage());
			folders = null;
		}
		return folders;
	}

	// get files by folder pour la génération des fichiers
	@PostMapping({ "/getFilesByFolder" })
	public List<CloturePaie> getFilesByFolder(@RequestBody Folder f) {
		try {
			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;
			String path;
			List<CloturePaie> files = clotureRepo.findByCategory(f.getFOLDERNAME());

			for (int i = 0; i < files.size(); i++) {

				path = files.get(i).getFOLDERPATH() + files.get(i).getFOLDERNAME() + File.separator + currentYear
						+ File.separator + dateFormat + File.separator + files.get(i).getPREFIXFILETYPE() + " "
						+ dateFormat + ".xlsx";

				files.get(i).setFOLDERPATH(path);

			}

			return files;
		} catch (Exception e) {
			System.out.println("Exception getFilesByFolder()==>" + e.getMessage());
		}
		return null;
	}

	@PostMapping({ "/getFilesByFolder2" })
	public List<CloturePaie> getFilesByFolder2(@RequestBody Folder f) {
		try {
			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;
			String path;
			List<CloturePaie> files = clotureRepo.findByCategory(f.getFOLDERNAME());
			List<CloturePaie> existFiles = new ArrayList();
			for (int i = 0; i < files.size(); i++) {

				path = files.get(i).getFOLDERPATH() + files.get(i).getFOLDERNAME() + File.separator + currentYear
						+ File.separator + dateFormat + File.separator + files.get(i).getPREFIXFILETYPE() + " "
						+ dateFormat + ".xlsx";

				files.get(i).setFOLDERPATH(path);

			}

			for (int j = 0; j < files.size(); j++) {
				File tblFile = new File(files.get(j).getFOLDERPATH());
				if (tblFile.exists()) {
					existFiles.add(files.get(j));
				}
			}
			return existFiles;
		} catch (Exception e) {
			System.out.println("Exception getFilesByFolder()==>" + e.getMessage());
		}
		return null;
	}

	// get etat files pour la génération des fichiers (recuperer les etats des
	// fichiers généré ou non)
	@PostMapping({ "getEtatFile" })
	public int getEtatFile(@RequestBody CloturePaie file) {
		File myfile = new File(file.getFOLDERPATH());
		if (myfile.exists()) {
			return 1;
		} else {
			return 0;
		}
	}

	@PostMapping({ "saveGeneratedGfiles" })
	public List<Gfile> saveGeneratedGfiles(@RequestBody List<Gfile> files) {
		try {
			List<Gfile> savedFiles = new ArrayList();

			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;
			for (int i = 0; i < files.size(); i++) {
				files.get(i).setIdpaymonth(currentDate.getId());
				files.get(i).setGfilename(files.get(i).getGfilename() + " " + dateFormat);

			}

			for (int i = 0; i < files.size(); i++) {
				Gfile myFile = gfileRepo.findByIdFilePaymonth(files.get(i).getIdfiletype(),
						files.get(i).getIdpaymonth());
				if (myFile != null) {
					myFile.setIduser(files.get(i).getIduser());
					myFile.setGfilegenerationdate(files.get(i).getGfilegenerationdate());
					savedFiles.add(gfileRepo.save(myFile));
				} else {
					savedFiles.add(gfileRepo.save(files.get(i)));
				}
			}
			return savedFiles;

		} catch (Exception e) {
			System.out.println("Exception saveGeneratedGfiles()==>" + e.getMessage());
		}
		return null;
	}

	// pour avoir tt les fichiers pour verifier l'etat de chacun (Cloture Paie Menu)
	@GetMapping({ "getAllCloturePaie" })
	public List<CloturePaie> getAllCloturePaie() {
		List<CloturePaie> toutLesCloturePaie = new ArrayList();
		try {
			toutLesCloturePaie = clotureRepo.findAll();
			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;
			String path;

			for (int i = 0; i < toutLesCloturePaie.size(); i++) {
					if(toutLesCloturePaie.get(i).getPREFIXFILETYPE().equals("NEWPAIE")||toutLesCloturePaie.get(i).getPREFIXFILETYPE().equals("PERS")) {
						path = toutLesCloturePaie.get(i).getFOLDERPATH() + toutLesCloturePaie.get(i).getFOLDERNAME()
								+ File.separator + currentYear + File.separator + dateFormat + File.separator
								+ toutLesCloturePaie.get(i).getPREFIXFILETYPE() + " " + dateFormat + ".dbf";
					}else {
						path = toutLesCloturePaie.get(i).getFOLDERPATH() + toutLesCloturePaie.get(i).getFOLDERNAME()
								+ File.separator + currentYear + File.separator + dateFormat + File.separator
								+ toutLesCloturePaie.get(i).getPREFIXFILETYPE() + " " + dateFormat + ".xlsx";
					}
			

				toutLesCloturePaie.get(i).setFOLDERPATH(path);

			}
			return toutLesCloturePaie;
		} catch (Exception e) {
			System.out.println("Exception getAllCloturePaie()==>" + e.getMessage());
			toutLesCloturePaie = null;
		}
		return toutLesCloturePaie;

	}

	public void generatedToDBF(String batPath, String filePath, String outPutPath) throws FileNotFoundException {
		File file = ResourceUtils.getFile("classpath:convert.sh");
		ProcessBuilder processBuilder = new ProcessBuilder(file.getAbsolutePath(), filePath, outPutPath);

		try {

			Process process = processBuilder.start();

			StringBuilder output = new StringBuilder();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				// System.out.println(line);
				output.append(line + "\n");
			}

			int exitVal = process.waitFor();
			// System.out.println(exitVal);
			if (exitVal == 0) {
				Files.deleteIfExists(Paths.get(filePath));  //***to delete xlsx files after generation of dbf files
				// System.out.println(output);
				// System.exit(0);

			} else {
				// abnormal...
				// System.out.println("prob"+exitVal);
			}

		} catch (IOException e) {
			System.out.println(e.getMessage() + "generatedToDBF");
		} catch (InterruptedException e) {
			System.out.println(e.getMessage() + "generatedToDBF");
		} catch (Exception e) {
			System.out.println(e.getMessage() + "generatedToDBF");
		}

	}

	// génération des fichiers Tables
	@GetMapping({ "generateTableFiles" })
	public List<CloturePaie> generateTableFiles() throws FileNotFoundException, JRException {
		List<CloturePaie> fileTables = new ArrayList();
		try {

			fileTables = clotureRepo.findByCategory("TABLES");

			List<Banque> lesBanques = banqueRepo.findAll();

			List<Diplome> lesDiplomes = diplomeRepo.findAll();

			List<Fonction> lesFonctions = fonctionRepo.findAll();

			List<Localite> lesLocalites = localiteRepo.findAll();

			List<Pays> lesPays = paysRepo.findAll();

			List<Rubrique> lesRubriques = rubriqueRepo.findAll();

			List<TabStructure> lesTabStructures = tabStructureRepo.findAll();

			// **********************************************get current date from payMonth

			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;

			// ********************************folder generation if not exist
			String pathWithYear = fileTables.get(0).getFOLDERPATH() + fileTables.get(0).getFOLDERNAME() + File.separator
					+ currentYear;
			String pathWithMounth = pathWithYear + File.separator + dateFormat;
			File fileYear = new File(pathWithYear);
			if (!fileYear.exists()) {
				fileYear.mkdir();
			}
			File fileMounth = new File(pathWithMounth);
			if (!fileMounth.exists()) {
				fileMounth.mkdir();
			}

			// load file and compile it
			File file = ResourceUtils.getFile("classpath:lesBanques.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lesBanques);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			Object outputFileName = pathWithMounth + File.separator
					+ clotureRepo.findByDesc("banque").get(0).getPREFIXFILETYPE() + " " + dateFormat + ".xlsx";
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName);
			exporter.exportReport();

			// generatedToDBF("",outputFileName.toString(),pathWithMounth);
			// load file and compile it
			File fileDiplome = ResourceUtils.getFile("classpath:lesDiplomes.jrxml");
			JasperReport jasperReport2 = JasperCompileManager.compileReport(fileDiplome.getAbsolutePath());
			JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(lesDiplomes);
			JasperPrint jasperPrint2 = JasperFillManager.fillReport(jasperReport2, null, dataSource2);
			JRXlsxExporter exporter2 = new JRXlsxExporter();
			exporter2.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter2.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter2.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint2);
			Object outputFileName2 = pathWithMounth + File.separator
					+ clotureRepo.findByDesc("diplome").get(0).getPREFIXFILETYPE() + " " + dateFormat + ".xlsx";
			exporter2.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName2);
			exporter2.exportReport();

			// generatedToDBF("",outputFileName2.toString(),pathWithMounth);
			// load file and compile it
			File fileFonctions = ResourceUtils.getFile("classpath:lesFonctions.jrxml");
			JasperReport jasperReport3 = JasperCompileManager.compileReport(fileFonctions.getAbsolutePath());
			JRBeanCollectionDataSource dataSource3 = new JRBeanCollectionDataSource(lesFonctions);
			JasperPrint jasperPrint3 = JasperFillManager.fillReport(jasperReport3, null, dataSource3);
			JRXlsxExporter exporter3 = new JRXlsxExporter();
			exporter3.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter3.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter3.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint3);
			Object outputFileName3 = pathWithMounth + File.separator
					+ clotureRepo.findByDesc("fonction").get(0).getPREFIXFILETYPE() + " " + dateFormat + ".xlsx";
			exporter3.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName3);
			exporter3.exportReport();
			// generatedToDBF("",outputFileName3.toString(),pathWithMounth);
			// load file and compile it
			File fileLocalite = ResourceUtils.getFile("classpath:lesLocalites.jrxml");
			JasperReport jasperReport4 = JasperCompileManager.compileReport(fileLocalite.getAbsolutePath());
			JRBeanCollectionDataSource dataSource4 = new JRBeanCollectionDataSource(lesLocalites);
			JasperPrint jasperPrint4 = JasperFillManager.fillReport(jasperReport4, null, dataSource4);
			JRXlsxExporter exporter4 = new JRXlsxExporter();
			exporter4.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter4.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter4.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint4);
			Object outputFileName4 = pathWithMounth + File.separator
					+ clotureRepo.findByDesc("localite").get(0).getPREFIXFILETYPE() + " " + dateFormat + ".xlsx";
			exporter4.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName4);
			exporter4.exportReport();
			// generatedToDBF("",outputFileName4.toString(),pathWithMounth);
			// load file and compile it
			File filePays = ResourceUtils.getFile("classpath:lesPays.jrxml");
			JasperReport jasperReport5 = JasperCompileManager.compileReport(filePays.getAbsolutePath());
			JRBeanCollectionDataSource dataSource5 = new JRBeanCollectionDataSource(lesPays);
			JasperPrint jasperPrint5 = JasperFillManager.fillReport(jasperReport5, null, dataSource5);
			JRXlsxExporter exporter5 = new JRXlsxExporter();
			exporter5.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter5.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter5.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint5);
			Object outputFileName5 = pathWithMounth + File.separator
					+ clotureRepo.findByDesc("pays").get(0).getPREFIXFILETYPE() + " " + dateFormat + ".xlsx";
			exporter5.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName5);
			exporter5.exportReport();
			// generatedToDBF("",outputFileName5.toString(),pathWithMounth);
			// load file and compile it
			File fileRubrique = ResourceUtils.getFile("classpath:lesRubriques.jrxml");
			JasperReport jasperReport6 = JasperCompileManager.compileReport(fileRubrique.getAbsolutePath());
			JRBeanCollectionDataSource dataSource6 = new JRBeanCollectionDataSource(lesRubriques);
			JasperPrint jasperPrint6 = JasperFillManager.fillReport(jasperReport6, null, dataSource6);
			JRXlsxExporter exporter6 = new JRXlsxExporter();
			exporter6.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter6.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter6.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint6);
			Object outputFileName6 = pathWithMounth + File.separator
					+ clotureRepo.findByDesc("rubrique").get(0).getPREFIXFILETYPE() + " " + dateFormat + ".xlsx";
			exporter6.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName6);
			exporter6.exportReport();
			// generatedToDBF("",outputFileName6.toString(),pathWithMounth);
			// load file and compile it
			File fileTabStructure = ResourceUtils.getFile("classpath:lesTabStructures.jrxml");
			JasperReport jasperReport7 = JasperCompileManager.compileReport(fileTabStructure.getAbsolutePath());
			JRBeanCollectionDataSource dataSource7 = new JRBeanCollectionDataSource(lesTabStructures);
			JasperPrint jasperPrint7 = JasperFillManager.fillReport(jasperReport7, null, dataSource7);
			JRXlsxExporter exporter7 = new JRXlsxExporter();
			exporter7.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter7.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter7.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint7);
			Object outputFileName7 = pathWithMounth + File.separator
					+ clotureRepo.findByDesc("structure").get(0).getPREFIXFILETYPE() + " " + dateFormat + ".xlsx";
			exporter7.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName7);
			exporter7.exportReport();
			// generatedToDBF("",outputFileName7.toString(),pathWithMounth);
			return fileTables;

		} catch (Exception e) {
			System.out.println(e.getMessage() + "==>generateTableFiles()");
			fileTables = null;
		}
		return fileTables;

	}

	// génération des fichiers System
	@GetMapping({ "generateSystemFiles" })
	public List<CloturePaie> generateSystemFiles() throws FileNotFoundException, JRException {
		List<CloturePaie> fileSys = new ArrayList();
		try {

			fileSys = clotureRepo.findByCategory("SYSTEME");
			List<Change> lesChanges = changRepo.findAll();
			List<Dep> lesDeps = depRepo.findAll();

			// **********************************************get current date from payMonth

			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;

			// ********************************folder generation if not exist
			String pathWithYear = fileSys.get(0).getFOLDERPATH() + fileSys.get(0).getFOLDERNAME() + File.separator
					+ currentYear;
			String pathWithMounth = pathWithYear + File.separator + dateFormat;
			File fileYear = new File(pathWithYear);
			if (!fileYear.exists()) {
				fileYear.mkdir();
			}
			File fileMounth = new File(pathWithMounth);
			if (!fileMounth.exists()) {
				fileMounth.mkdir();
			}
			// load file and compile it
			File fileChange = ResourceUtils.getFile("classpath:change.jrxml");
			JasperReport jasperReport8 = JasperCompileManager.compileReport(fileChange.getAbsolutePath());
			JRBeanCollectionDataSource dataSource8 = new JRBeanCollectionDataSource(lesChanges);
			JasperPrint jasperPrint8 = JasperFillManager.fillReport(jasperReport8, null, dataSource8);
			JRXlsxExporter exporter8 = new JRXlsxExporter();
			exporter8.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter8.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter8.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint8);
			Object outputFileName8 = pathWithMounth + File.separator
					+ clotureRepo.findByDesc("change").get(0).getPREFIXFILETYPE() + " " + dateFormat + ".xlsx";
			exporter8.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName8);
			exporter8.exportReport();
			// generatedToDBF("",outputFileName8.toString(),pathWithMounth);
			// load file and compile it
			File fileDep = ResourceUtils.getFile("classpath:dep.jrxml");
			JasperReport jasperReport9 = JasperCompileManager.compileReport(fileDep.getAbsolutePath());
			JRBeanCollectionDataSource dataSource9 = new JRBeanCollectionDataSource(lesDeps);
			JasperPrint jasperPrint9 = JasperFillManager.fillReport(jasperReport9, null, dataSource9);
			JRXlsxExporter exporter9 = new JRXlsxExporter();
			exporter9.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter9.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter9.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint9);
			Object outputFileName9 = pathWithMounth + File.separator
					+ clotureRepo.findByDesc("dep").get(0).getPREFIXFILETYPE() + " " + dateFormat + ".xlsx";
			exporter9.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName9);
			exporter9.exportReport();
			// generatedToDBF("",outputFileName9.toString(),pathWithMounth);
			return fileSys;

		} catch (Exception e) {
			System.out.println(e.getMessage() + "==>generateSystemFiles()");
			fileSys = null;
		}
		return fileSys;

	}

	// génération des fichiers NewPaie
	@GetMapping({ "generateNewPaieFiles" })
	public List<CloturePaie> generateNewPaieFiles() throws FileNotFoundException, JRException {
		List<CloturePaie> fileNewPaie = new ArrayList();
		try {

			fileNewPaie = clotureRepo.findByCategory("NEWPAIE");

			List<NewPaie> lesNewpaie = newPaieRepo.findAll();
			
			// **********************************************get current date from payMonth

			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;

			// ********************************folder generation if not exist
			String pathWithYear = fileNewPaie.get(0).getFOLDERPATH() + fileNewPaie.get(0).getFOLDERNAME()
					+ File.separator + currentYear;
			String pathWithMounth = pathWithYear + File.separator + dateFormat;
			File fileYear = new File(pathWithYear);
			if (!fileYear.exists()) {
				fileYear.mkdir();
			}
			File fileMounth = new File(pathWithMounth);
			if (!fileMounth.exists()) {
				fileMounth.mkdir();
			}

			File fileNewpaie = ResourceUtils.getFile("classpath:newPaie.jrxml");
			JasperReport jasperReport10 = JasperCompileManager.compileReport(fileNewpaie.getAbsolutePath());
			JRBeanCollectionDataSource dataSource10 = new JRBeanCollectionDataSource(lesNewpaie);
			JRSwapFileVirtualizer virtualizer = null;
			virtualizer = new JRSwapFileVirtualizer(3, new JRSwapFile(SwapPath, 2048, 1024), false);
			Map<String, Object> param = new HashMap<>();
			param.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			JasperPrint jasperPrint10 = JasperFillManager.fillReport(jasperReport10, param, dataSource10);
			JRXlsxExporter exporter10 = new JRXlsxExporter();
			exporter10.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter10.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter10.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint10);
			Object outputFileName10 = pathWithMounth + File.separator
					+ clotureRepo.findByDesc("newpaie").get(0).getPREFIXFILETYPE() + " " + dateFormat + ".xlsx";
			exporter10.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName10);
			exporter10.exportReport();
			generatedToDBF("", outputFileName10.toString(), pathWithMounth);
			return fileNewPaie;
		} catch (Exception e) {
			System.out.println(e.getMessage() + "==>generateNewPaieFiles()");
			fileNewPaie = null;
		}
		return fileNewPaie;

	}

	// génération des fichiers Pers
	@GetMapping({ "generatePersFiles" })
	public List<CloturePaie> generatePersFiles() throws FileNotFoundException, JRException {
		List<CloturePaie> filesPers = new ArrayList();
		try {

			filesPers = clotureRepo.findByCategory("PERS");
			// System.out.println( filesPers.get(0).getFOLDERPATH().trim());

			List<Pers> lesPers = persRepo.findAll();

			// **********************************************get current date from payMonth

			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;

			// ********************************folder generation if not exist
			String pathWithYear = filesPers.get(0).getFOLDERPATH().trim() + filesPers.get(0).getFOLDERNAME().trim()
					+ File.separator + currentYear;
			String pathWithMounth = pathWithYear + File.separator + dateFormat;
			File fileYear = new File(pathWithYear);
			if (!fileYear.exists()) {
				fileYear.mkdir();
			}
			File fileMounth = new File(pathWithMounth);
			if (!fileMounth.exists()) {
				fileMounth.mkdir();
			}
			// System.out.println(pathWithMounth);
			// load file and compile it
			File filePers = ResourceUtils.getFile("classpath:pers.jrxml");
			JasperReport jasperReport11 = JasperCompileManager.compileReport(filePers.getAbsolutePath());
			JRBeanCollectionDataSource dataSource11 = new JRBeanCollectionDataSource(lesPers);
			JRSwapFileVirtualizer virtualizer = null;
			virtualizer = new JRSwapFileVirtualizer(3, new JRSwapFile(SwapPath, 2048, 1024), false);
			Map<String, Object> param = new HashMap<>();
			param.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			JasperPrint jasperPrint11 = JasperFillManager.fillReport(jasperReport11, param, dataSource11);
			JRXlsxExporter exporter11 = new JRXlsxExporter();
			exporter11.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
			exporter11.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 40);
			exporter11.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint11);
			Object outputFileName11 = pathWithMounth + File.separator
					+ clotureRepo.findByDesc("pers").get(0).getPREFIXFILETYPE() + " " + dateFormat + ".xlsx";
			exporter11.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName11);

			exporter11.exportReport();
			generatedToDBF("", outputFileName11.toString(), pathWithMounth);
			return filesPers;
		} catch (Exception e) {
			System.out.println(e.getMessage() + "==>generatePersFiles()");
			filesPers = null;
		}
		return filesPers;

	}

	/************************************************
	 * Login/subscribe
	 *********************************************************************************************************/

	// Connexion

	@RequestMapping(value = "/api/auth/signin", method = RequestMethod.POST)
	// @PostMapping("/api/auth/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

		Optional<User> currentUser = userRepository.findByUsername(loginRequest.getUsername());

		if (currentUser.get().getState() == 1) {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String jwt = jwtProvider.generateJwtToken(authentication);

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername()));
		} else {
			String error = "xx" + "." + "yy" + "." + "zz";
			return ResponseEntity.ok(new JwtResponse(error, currentUser.get().getUsername()));
		}

	}

	// Inscription
	@RequestMapping(value = "/api/auth/signup", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()), 0);

		userRepository.save(user);

		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}

	/*****************************************
	 * gestion users (plus tard)
	 ************************************************************************************************/
	@GetMapping({ "getAllUsers" })
	public List<User> getAllUsers() {
		List<User> allUsers = new ArrayList();
		try {
			allUsers = userRepository.findAll();
			return allUsers;
		} catch (Exception e) {
			System.out.println("Exception while getting all users==>" + e.getMessage());
			allUsers = null;
		}
		return allUsers;
	}

	@PostMapping({ "updateUser" })
	public Optional<User> updateUser(@PathVariable Integer id) {
		Optional<User> currentUser = userRepository.findById(id);
		currentUser.get().setState(1);
		// implementer l'insertion au niveau de la table ROLEUSERS
		return currentUser;
	}

	/****************************************************
	 * Parametres
	 *****************************************************************************************************/
	// **************************************************************************Update
	// PSW**************************************************************************
	// get User by username
	@PostMapping({ "getUserByUserName" })
	public User getUserByUserName(@RequestBody User u) {
		try {
			Optional<User> user = userRepository.findByUsername(u.getUsername());
			if (user.get() != null) {
				// System.out.println(user.get().getIduser());
				return user.get();
			}
		} catch (Exception e) {
			System.out.println("Exception getUserByUserName()==>" + e.getMessage());
		}
		return null;
	}

	// for settings : changer psw
	@PostMapping({ "updatePsw" })
	public User updatePsw(@RequestBody User u) {
		User user = new User();
		try {
			Optional<User> currentUser = userRepository.findByUsername(u.getUsername());

			if (currentUser != null) {
				currentUser.get().setPassword(encoder.encode(u.getPassword()));
				user.setIduser(currentUser.get().getIduser());
				user.setEmail(currentUser.get().getEmail());
				user.setName(currentUser.get().getName());
				user.setPassword(currentUser.get().getPassword());
				user.setState(currentUser.get().getState());
				user.setUsername(currentUser.get().getUsername());
				userRepository.save(user);
			}
			return user;
		} catch (Exception e) {
			System.out.println("Exception while updating Psw==>" + e.getMessage());
			user = null;
		}
		return user;

	}

	// for settings : changer psw (comparer l'ancien psw pour permettre l'update du
	// psw
	@PostMapping({ "comparePsw" })
	public User comparePsw(@RequestBody User u) {
		User user = new User();
		try {
			Optional<User> currentUser = userRepository.findByUsername(u.getUsername());

			if (currentUser != null) {
				String ancienPsw = encoder.encode(u.getPassword());
				if (currentUser.get().getPassword() == ancienPsw) {
					user.setState(1);
					;
				} else {
					user.setState(-1);
				}
				return user;
			}

		} catch (Exception e) {
			System.out.println("Exception while updating Psw==>" + e.getMessage());
			user = null;
		}
		return user;

	}

	// **************************************************************************File
	// to
	// print**************************************************************************
	// for settings : avoir tout les etats paie pour séléctionner les etat à
	// imprimer(file to print)
	@GetMapping({ "allEtats" })
	public List<CloturePaie> getAllEtat() {
		List<CloturePaie> toutLesEtats = new ArrayList();
		try {

			// ***************************get all files to generate and its directory path
			toutLesEtats = clotureRepo.findByDesc("etat");
			return toutLesEtats;

		} catch (Exception e) {

			System.out.println("Exception getAllEtat():   " + e.getMessage());
			toutLesEtats = null;
		}

		return toutLesEtats;
	}

	// for settings:pour avoir l'etat actuel des FileToPrint
	@GetMapping({ "allFileToPrint" })
	public List<FileToPrint> getAllFileToPrint() {
		List<FileToPrint> files = new ArrayList();
		try {
			files = fileToPrintRepo.findAll();
			return files;
		} catch (Exception e) {
			System.out.println("Exception while getting all file to print==>" + e.getMessage());
			files = null;
		}
		return files;
	}

	// for settings : pour avoir les etats selectionnés pour chaque structure (file
	// to print)
	@PostMapping({ "selectedEtats" })
	public List<FileToPrint> getSelectedEtat(@RequestBody FileToPrint f) {
		List<FileToPrint> files = new ArrayList();
		try {
			files = fileToPrintRepo.findByStructure(f.getIdStructure());

			return files;
		} catch (Exception e) {
			System.out.println("Exception while getting getSelectedEtat==>" + e.getMessage());
			files = null;
		}
		return files;
	}

	// for settings: to save file to print foreach sturucture choosed by user
	@PostMapping({ "saveFileToPrint" })
	public List<FileToPrint> saveFileToPrint(@RequestBody List<FileToPrint> files) {
		Date date = new Date();
		try {
			for (int i = 0; i < files.size(); i++) {
				FileToPrint myFile = new FileToPrint();
				myFile.setAddedDate(date);
				myFile.setIdFileType(files.get(i).getIdFileType());
				myFile.setIdStructure(files.get(i).getIdStructure());

				fileToPrintRepo.save(myFile);
			}
			return files;
		} catch (Exception e) {
			System.out.println("Exception while saving file to print ==>" + e.getMessage());
		}

		return null;

	}

	// for settings: to delete files to print unchecked by user
	@PostMapping({ "deleteFileToPrint" })
	public List<FileToPrint> deleteFileToPrint(@RequestBody List<FileToPrint> files) {
		try {
			for (int i = 0; i < files.size(); i++) {

				fileToPrintRepo.delete(files.get(i));
				;
			}
			return files;
		} catch (Exception e) {
			System.out.println("Exception while deleting file to print ==>" + e.getMessage());
		}

		return null;
	}

	// for settings: to have all shactivities (file to print)
	@GetMapping({ "allShActivities" })
	public List<ShActivity> getAllShActivities() {
		try {
			return shActivityRepo.findAll();
		} catch (Exception e) {
			System.out.println("Exception while getting shActivities ==>" + e.getMessage());
		}

		return null;
	}

	// **************************************************************************Gestion
	// structure**************************************************************************
	// update structure
	@PostMapping({ "/updateStructure" })
	public Structure updateStructure(@RequestBody Structure structure) {
		Optional<Structure> structureToUpdate = null;
		try {
			structureToUpdate = structureRepo.findById(structure.getIDSTRUCTURE());
			if (structureToUpdate.get() != null) {
				if (structure.getEMAILGROUPMANAGERS() != "") {
					structureToUpdate.get().setEMAILGROUPMANAGERS(structure.getEMAILGROUPMANAGERS());
				}
				if (structure.getIDACTIVITY() != 0) {
					structureToUpdate.get().setIDACTIVITY(structure.getIDACTIVITY());
				}
				if (structure.getSTRUCTURENAME() != "") {
					structureToUpdate.get().setSTRUCTURENAME(structure.getSTRUCTURENAME());
				}
				if (structure.getSTRUCTURECODELIKE() != "") {
					structureToUpdate.get().setSTRUCTURECODELIKE(structure.getSTRUCTURECODELIKE());
				}
				if (structure.getSTRUCTURECODENOTLIKE() != "") {
					structureToUpdate.get().setSTRUCTURECODENOTLIKE(structure.getSTRUCTURECODENOTLIKE());
				}
				if (structure.getFICHIERCODELIKE() != "") {
					structureToUpdate.get().setFICHIERCODELIKE(structure.getFICHIERCODELIKE());
				}
				if (structure.getFICHIERCODENOTLIKE() != "") {
					structureToUpdate.get().setFICHIERCODENOTLIKE(structure.getFICHIERCODENOTLIKE());
				}
				// System.out.println(structureToUpdate.get().getIDACTIVITY()+"
				// "+structureToUpdate.get().getIDSTRUCTURE());
				structureRepo.save(structureToUpdate.get());
				return structureToUpdate.get();
			}
		} catch (Exception e) {
			System.out.println("Exception updateStructure()==>" + e.getMessage());
			structureToUpdate = null;
		}
		return null;
	}

	// update structure Archive
	@PostMapping({ "/updateStructureArchive" })
	public ArchiveStructure updateStructureArchive(@RequestBody ArchiveStructure archivateStructure) {
		try {
			archiveStructureRepo.save(archivateStructure);
			return archivateStructure;
		} catch (Exception e) {
			System.out.println("Exception updateStructureArchive()==>" + e.getMessage());
		}
		return null;
	}

	// add structure
	@PostMapping({ "/addStructure" })
	public Structure addStructure(@RequestBody Structure structure) {
		try {
			structureRepo.save(structure);
			return structure;
		} catch (Exception e) {
			System.out.println("Exception addStructure()==>" + e.getMessage());
		}

		return null;

	}

	// archivage add structure
	@PostMapping({ "/addArchiveStructure" })
	public ArchiveStructure addArchiveStructure(@RequestBody ArchiveStructure structure) {
		try {
			Structure currentStructure = structureRepo.findByName(structure.getArchstructurename());
			if (currentStructure != null) {
				structure.setIdstructure(currentStructure.getIDSTRUCTURE());
				archiveStructureRepo.save(structure);
				return structure;
			}

		} catch (Exception e) {
			System.out.println("Exception addStructure()==>" + e.getMessage());
		}

		return null;

	}

	// delete structure with archive
	@PostMapping({ "deleteStructure" })
	public Structure deleteStructure(@RequestBody ArchiveStructure structure) {
		try {
			Optional<Structure> deletedStructure = structureRepo.findById(structure.getIdstructure());

			if (deletedStructure.get() != null) {
				// System.out.println(deletedStructure.get().getEMAILGROUPMANAGERS());
				archiveStructureRepo.save(structure);
				deletedStructure.get().setSTATUSSTRUCTURE(-1);
				structureRepo.save(deletedStructure.get());

				return deletedStructure.get();
			}
		} catch (Exception e) {
			System.out.println("Exception deleteStructure()==>" + e.getMessage());
		}
		return null;
	}
//**************************************************************************Gestion Fichier**************************************************************************
	// get all folders(MAJ path )
	// on trouve cette api dans la section cloture paie

	// get all files to select and put in folder
	@GetMapping({ "getAllFileType" })
	public List<FileType> getAllFileType() {
		try {
			return fileTypeRepo.findAll();
		} catch (Exception e) {
			System.out.println("Exception getAllFileType()==>" + e.getMessage());
		}
		return null;
	}

	// delete folder path
	@PostMapping({ "deleteFolderPath" })
	public Folder deleteFolderPath(@RequestBody FolderArchive folderArchive) {
		try {

			folderArchiveRepo.save(folderArchive);
			Folder folder = folderRepo.findByFolderName(folderArchive.getArchfoldername());
			folder.setSTATUSFOLDER(-1);
			folderRepo.save(folder);
			List<FileTypeToFolder> allTypeToFolder = fileTypeToFolderRepo.findByIdFolder(folder.getIDFOLDER());
			if (allTypeToFolder != null) {
				for (int i = 0; i < allTypeToFolder.size(); i++) {
					fileTypeToFolderRepo.delete(allTypeToFolder.get(i));
				}
				return folder;
			}

		} catch (Exception e) {
			System.out.println("Exception deleteFolderPath()==>" + e.getMessage());
		}
		return null;
	}

	// update Folder path
	@PostMapping({ "updateFolderPath" })
	public Folder updateFolderPath(@RequestBody Folder folder) {
		try {

			folderRepo.save(folder);

			return folder;

		} catch (Exception e) {
			System.out.println("Exception updateFolderPath()==>" + e.getMessage());
		}
		return null;
	}

	@PostMapping({ "updateFolderPathArchive" })
	public FolderArchive updateFolderPathArchive(@RequestBody FolderArchive folderArchive) {
		try {

			folderArchiveRepo.save(folderArchive);
			return folderArchive;

		} catch (Exception e) {
			System.out.println("Exception updateFolderPath()==>" + e.getMessage());
		}
		return null;
	}

	@PostMapping({ "addNewFolder" })
	public Folder addNewFolder(@RequestBody Folder folder) {
		try {

			folderRepo.save(folder);

			return folder;

		} catch (Exception e) {
			System.out.println("Exception addNewFolder()==>" + e.getMessage());
		}
		return null;
	}

	@PostMapping({ "addNewFolderArchive" })
	public FolderArchive addNewFolderArchive(@RequestBody FolderArchive folderArchive) {
		try {

			Folder folder = folderRepo.findByFolderName(folderArchive.getArchfoldername());
			if (folder != null) {
				folderArchive.setIdfolder(folder.getIDFOLDER());
				folderArchiveRepo.save(folderArchive);
				return folderArchive;
			}

		} catch (Exception e) {
			System.out.println("Exception addNewFolderArchive()==>" + e.getMessage());
		}
		return null;
	}

	@PostMapping({ "addFilesToNewFolder" })
	public List<FileTypeToFolder> addFilesToNewFolder(@RequestBody List<FileTypeToFolder> files) {
		try {

			for (int i = 0; i < files.size(); i++) {
				fileTypeToFolderRepo.save(files.get(i));
			}
			return files;

		} catch (Exception e) {
			System.out.println("Exception addFilesToNewFolder()==>" + e.getMessage());
		}
		return null;
	}

	// **************************************************************************Controle
	// Generation
	// Etats/fichier**************************************************************************
	@GetMapping({ "getNotGeneratedStructure" })
	public List<Structure> getNotGeneratedStructure() {
		try {
			// to get folder path Etat+fichier
			Folder folderEtat = folderRepo.findByFolderName("ETAT");
			// get current paymonth
			PayMonth currentDate = paymonthRepo.findByState();
			String currentYear = currentDate.getPaymonth().substring(0, 4);
			String currentMonth = currentDate.getPaymonth().substring(4, 6);
			String dateFormat = currentYear + "-" + currentMonth;
			// current path
			String path = folderEtat.getFOLDERPATH() + folderEtat.getFOLDERNAME() + File.separator + currentYear
					+ File.separator + dateFormat;
			// get all structure to check them all
			List<Structure> allStructures = structureRepo.findByStatus(-1);
			// list of structure to return
			List<Structure> structuresToReturn = new ArrayList<>();
			for (int i = 0; i < allStructures.size(); i++) {
				// path with structure folder
				String pathWithStructure = path + File.separator + allStructures.get(i).getSTRUCTURENAME() + " "
						+ dateFormat;
				File folderToCheck = new File(pathWithStructure);

				File[] children = folderToCheck.listFiles();
				if (children == null) {
					return allStructures;
				} else {
					if (children.length != 8) {
						structuresToReturn.add(allStructures.get(i));
					}
				}

			}
			return structuresToReturn;
		} catch (Exception e) {
			System.out.println("Exception getNotGeneratedStructure()==>" + e.getMessage());
		}

		return null;
	}

	/****************************************************
	 * Send Etats Fichiers
	 *****************************************************************************************************/
	@GetMapping({ "getAllFichiers" })
	public List<CloturePaie> getAllFichiers() {
		List<CloturePaie> toutLesFichiers = new ArrayList();
		try {

			// ***************************get all files to generate and its directory path
			toutLesFichiers = clotureRepo.findByFileCat(4);
			return toutLesFichiers;

		} catch (Exception e) {

			System.out.println("Exception getAllFichiers():   " + e.getMessage());

		}

		return null;
	}

	public String ZipMultipleFiles(String[] srcFiles, String zipPath) {
		try {
			FileOutputStream fos = new FileOutputStream(zipPath);
			ZipOutputStream zipOut = new ZipOutputStream(fos);
			for (String srcFile : srcFiles) {
				File fileToZip = new File(srcFile);
				FileInputStream fis = new FileInputStream(fileToZip);
				ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
				zipOut.putNextEntry(zipEntry);

				byte[] bytes = new byte[1024];
				int length;
				while ((length = fis.read(bytes)) >= 0) {
					zipOut.write(bytes, 0, length);
				}
				fis.close();
			}
			zipOut.close();
			fos.close();
			return "success";
		} catch (IOException e) {
			System.out.println("Exception ZipMultipleFiles()==> " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception ZipMultipleFiles()==> " + e.getMessage());
		}
		return null;
	}

	@PostMapping({ "sendEtats" })
	public MailResponse sendEtats(@RequestBody MailRequest request, @RequestParam String zipPath) {
		try {
			// to replace "\\" in paths with File.separator (Windows/linux)
			for (int i = 0; i < request.getFilesName().length; i++) {
				request.getFilesName()[i]=request.getFilesName()[i].replace("\\", File.separator);

			}
			zipPath=zipPath.replace("\\", File.separator);

			String Zipresponse = ZipMultipleFiles(request.getFilesName(), zipPath);
			if (Zipresponse != null) {
				Map<String, Object> model = new HashMap<>();
				model.put("msg", request.getMsg());
				String zipName = request.getSturcturename();
				MailResponse response = service.sendEmailZip(request, zipPath, zipName, model);
				return response;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception sendEtats==> " + e.getMessage());
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception sendEtats==> " + e.getMessage());
		}
		return null;
	}

}
