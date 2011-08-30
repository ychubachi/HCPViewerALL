/*
 * @(#)HScanedLine.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.scanner;

import compiler.HCompileOptions;

/**
 * @author Manabu Sugiura
 * @version $Id: HSpaceConvertedLine.java,v 1.2 2004/11/25 05:00:47 gackt Exp $
 */
public class HSpaceConvertedLine {

	private String indentPart = null;
	private String bodyPart = null;

	/**
	 * �R���X�g���N�^
	 */
	public HSpaceConvertedLine(String s) {
		splitPart(s);
	}

	private void splitPart(String s) {
		//�C���f���g�����ƕ����񕔕��𕪊�����
		for (int i = 0; i < s.length(); i++) {
			switch (s.charAt(i)) {
				case '�@' :
				case ' ' :
				case '\t' :
					break;
				default : //�{�����n�܂���
					indentPart = s.substring(0, i);
					bodyPart = s.substring(i);
					return;
			}
		}

		//�C���f���g�����̏ꍇ
		indentPart = s;
		bodyPart = "";
	}

	public void replaceSpaceToTab() {
		HCompileOptions options = HCompileOptions.getInstance();

		//�C���f���g�����̈�萔�A������X�y�[�X��Tab�ɒu��
		//���K�\���𗘗p���܂����g {�S�p�X�y�[�X�̘A����}?| {���p�X�y�[�X�̘A����}?�h
		String regex = "�@{" + options.getFullSpaceSizeForATab() + "}?| {"
				+ options.getHalfSpaceSizeForATab() + "}?";
		indentPart = indentPart.replaceAll(regex, "\t");
	}

	public String toString() {
		return indentPart + bodyPart;
	}

}