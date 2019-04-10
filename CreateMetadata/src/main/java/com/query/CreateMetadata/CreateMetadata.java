package com.query.CreateMetadata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CreateMetadata {
	public static void main(String[] args) throws Exception {
		Map<String, String> topicMap = new HashMap<String, String>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			File topicList = new File("C:\\Users\\t.jain\\Desktop\\CREATE_METADATA\\topicwithi.txt");
			Scanner topic = new Scanner(topicList);
			while (topic.hasNextLine()) {
				String topicName = topic.nextLine();
				String[] t = topicName.split("->");
				topicMap.put(t[0], t[1]);
			}
			topic.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		map.put("Analgesic", 2);
		map.put("Antibiotic", 3);
		map.put("Cardiovascular", 4);
		map.put("Dermatology", 5);
		// map.put("Endocrinology", 6);
		map.put("Gastrointestinal", 7);
		map.put("Neurology", 8);
		map.put("Oral and Dental", 9);
		map.put("Palliative Care", 10);
		map.put("Psychotropic", 11);
		map.put("Rheumatology", 12);
		map.put("Toxicology and Wilderness", 13);
		map.put("Ulcer and Wound Management", 14);
		map.put("Fatigue", 17);
		map.put("Respiratory", 19);
		map.put("Other", 24);
		map.put("Quicklinks", 25);
		map.put("Diabetes", 26);
		map.put("Sexual and Reproductive Health", 27);
		map.put("Bone and Metabolism", 28);

		try {
			File input = new File("C:\\Users\\t.jain\\Desktop\\CREATE_METADATA\\sql.txt");
			File output = new File("C:\\Users\\t.jain\\Desktop\\CREATE_METADATA\\out.txt");
			PrintWriter printer = new PrintWriter(output);
			Scanner sc = new Scanner(input);
			printer.write(
					"INSERT INTO cdp_topic_content (guideline_id,topic_title,topic_code,topic_version_number,topic_filename,topic_order_no,toc_group_id) VALUES");
			printer.write("\n");
			while (sc.hasNextLine()) {
				String s = sc.nextLine();
				String[] t = s.split(",");

				String topic_title;
				String guideline_name;
				String topic_code;
				String topic_filename;
				String topic_version_number;
				String topic_order_no;
				String toc_group_id;

				if (t.length != 0 && t.length > 11) {
					StringBuilder topic_title_temp = new StringBuilder(t[3].toString());
					topic_title_temp = topic_title_temp.append(",");
					topic_title_temp = topic_title_temp.append(t[4].toString());
					topic_title = topic_title_temp.toString();

					guideline_name = t[5];
					topic_code = t[7];
					topic_filename = t[8];
					topic_version_number = "7";
					topic_order_no = t[9];
					toc_group_id = t[11];
				} else {

					topic_title = t[3];
					guideline_name = t[4];
					topic_code = t[6];
					topic_filename = t[7];
					topic_version_number = "7";
					topic_order_no = t[8];
					toc_group_id = t[10];
				}
				for (String guideline : map.keySet()) {
					int key = map.get(guideline);
					if (guideline_name.contains(guideline)) {
						printer.write("(" + key + ",");
					}

				}

				/*
				 * for (String topic : topicMap.keySet()) { String value =
				 * topicMap.get(topic).trim(); topic = topic.trim(); if
				 * (topic_title.contains(topic)) { topic_title = "'" + value +
				 * "'"; System.out.println(topic + " <-Replaced with:-> " +
				 * value); }
				 * 
				 * }
				 */

				printer.write(topic_title + ",");
				printer.write(topic_code + ",");
				printer.write(topic_version_number + ",");
				printer.write(topic_filename + ",");
				printer.write(topic_order_no + ",");
				printer.write(toc_group_id);
				printer.write(",");

				printer.write("\n");

			}
			sc.close();
			printer.close();
			System.out.println("Query created.");
		} catch (FileNotFoundException e) {
			System.err.println("File not found. Please scan in new file.");
		}

	}
}