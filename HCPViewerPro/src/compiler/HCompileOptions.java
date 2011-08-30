/*
 * @(#)HCompileOptions.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler;

import java.io.OutputStream;

/**
 * �R���p�C�����̃I�v�V����
 * �E�R���p�C�����̃��b�Z�[�W�̏o�͐�
 * �E�X�y�[�X�i�S�p�E���p�j�������^�u1�Ƀ}�b�s���O���邩
 * @author Manabu Sugiura
 * @version $Id: HCompileOptions.java,v 1.4 2004/11/25 05:00:46 gackt Exp $
 */
public class HCompileOptions {

	/***********************
	 * �N���X�ϐ�
	 ***********************/

	//�f�t�H���g
	private static final OutputStream DEFAULT_STREAM = System.err;
	private static final int DEFAULT_FULL_SPACE_SIZE_FOR_A_TAB = 2;
	private static final int DEFAULT_HALF_SPACE_SIZE_FOR_A_TAB = 4;

	private static HCompileOptions instance = null;

	private static OutputStream stream = DEFAULT_STREAM;
	private static int fullSpaceSizeForATab = DEFAULT_FULL_SPACE_SIZE_FOR_A_TAB;
	private static int halfSpaceSizeForATab = DEFAULT_HALF_SPACE_SIZE_FOR_A_TAB;

	/**
	 * �R���X�g���N�^
	 */
	private HCompileOptions() {
		//Do nothing.
	}

	public static HCompileOptions getInstance() {
		if (instance == null) {
			instance = new HCompileOptions();
		}
		return instance;
	}

	public void setStream(OutputStream newStream) {
		stream = newStream;
	}

	public OutputStream getStream() {
		return stream;
	}

	public static void setFullSpaceSizeForATab(int newFullSpaceSizeForATab) {
		if (newFullSpaceSizeForATab <= 0) {
			throw new HCompileException("�S�p�X�y�[�X����0�ȏ��ݒ肵�Ă�������");
		}
		fullSpaceSizeForATab = newFullSpaceSizeForATab;
	}

	public int getFullSpaceSizeForATab() {
		return fullSpaceSizeForATab;
	}

	public static void setHalfSpaceSizeForATab(int newHalfSpaceSizeForATab) {
		if (newHalfSpaceSizeForATab <= 0) {
			throw new HCompileException("���p�X�y�[�X����0�ȏ��ݒ肵�Ă�������");
		}
		halfSpaceSizeForATab = newHalfSpaceSizeForATab;
	}

	public int getHalfSpaceSizeForATab() {
		return halfSpaceSizeForATab;
	}

}