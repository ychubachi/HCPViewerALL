######################################################
#
#   HCP Viewer Professional 
#   Version 1.4.2 (2010/05/31 公開)
#   READ ME
#   Copyright 2004-2010 Yoshiaki Matsuzawa, Manabu Sugiura
#   matsuzawa@inf.shizuoka.ac.jp
#   macchan@crew.sfc.keio.ac.jp
#   gackt@crew.sfc.keio.ac.jp
#
######################################################

＜ライセンス＞
・本ソフトウェアはフリーソフトです．
	1) GPL(the GNU General　Public License)に示された条件で本プログラムを再配布できます．
	2) 法人での商用利用, 大学の授業における利用については作者まで一報いただけると助かります．
	3) ソースコードの入手に際しては作者に連絡ください．無償で提供致します．改変も自由にできます．

・利用しているライブラリのライセンスについて	
	1)本ソフトウェアはApache Batikライブラリを同梱・利用しています．
		ライブラリの使用に関しては，Apache License 2.0に基づき，LICENSE, NOTICEファイルを付与します．
	2)本ソフトウェアはMozilla j-chardet1.1 を利用したライブラリ MEncodingDetectorを同梱・利用しています．
		ライブラリの使用に関しては，GPLライセンスを適用します．
	3)本ソフトウェアはPaul James Mutton(http://www.jibble.org/),EpsGraphicsライブラリを同梱・利用しています．
		ライブラリの使用に関しては，GPLライセンスを適用します．

・GPLライセンス適用表示		
    This file is part of HCP Viewer Professional.

    HCP Viewer Professional is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    HCP Viewer Professional  is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with HCP Viewer Professional .  If not, see <http://www.gnu.org/licenses/>.		

＜動作条件＞
Java Runtime Environment(JRE)またはJDKが必要です．
Javaのバージョンは1.5以上で動作を確認しています．

＜免責事項＞
本ソフトウェアプログラムは無保証です．
作者はプログラム自身のバグあるいは本プログラムの実行などから発生する
いかなる損害に対しても責任を持ちません．

＜ホームページ＞
http://www.crew.sfc.keio.ac.jp/projects/2004hcpviewer

＜マニュアルについて＞
・Professionalバージョンにはマニュアルがまだありません．しかし，操作方法はシンプルなので，
作者が授業で学生に使わせている限りは，特に問題は出ていません．
・新機能に関しては，サンプルコード（二分岐探索プログラム.hcp, BMI計算プログラム）を参照してください．
・上記ホームページに古いバージョンのマニュアルがあり，概ねそれと同様に使えます．

＜インストールと使い方＞
1．インストール
・任意のフォルダに展開していただいて結構です．

2．起動
・hcpviewer.jarをダブルクリックで起動できます．

3.ファイルの作成
・好みのテキストエディタで，インデントされたテキストファイルを拵えてください．
・「二分岐探索プログラム.hcp」はサンプルファイルです．
・拡張子は必ずしも「.hcp」である必要はありません．

4.ファイルの読み込み
・「ファイル」「開く」でファイルが読み込めます．
・エクスプローラ（MacではFinder）から，表示させたいファイルをHCPViewerのウインドウへ
ドロップインすることもできます．

5.エディタ機能
・「表示」→「エディタ」でテキストエディタを開くことが出来ます．
・保存すると，Viewerの方に反映されます．

6.コマンドラインツール の使用
・hcp2png, hcp2svg, hcp2pngall, hcp2svgallコマンドがwindowsで使えます．
・環境変数を作成し，HCP_VIEWER_HOME=[インストールした場所]にしてください．
・環境変数PATHに%HCP_VIEWER_HOME%\binを追加してください．

ご不明な点があれば，作者までメールを送付ください．

＜Eclipseプラグインについて＞
0.Eclipseのバージョン
3.5 (Galileo build-id:20100218-1602) で動作確認しています．

1．インストール
jp.ac.keio.sfc.crew.hcpviewerplugin_x.x.x.jar
をeclipse/pluginsフォルダに移動し，Eclipseを再起動してください．

2．起動
.hcpという拡張子をつけたファイルをhcpviewerプラグインがインストールされた
Eclipse上で開くと，HCPViewerが開きます．
(3.4.x系のEclipseエディタでの実装は不安定なため，一時的にdisableにしてあります．）

＜リリースノート＞
・ 1.4.2 2010/05/31
	・エディタを開いていないのに保存でき，エラーとなる問題を修正
	・文字コードDetectionに失敗するとエラーとなる問題を修正
	・文字コードDetectionの使い方に問題があり，UTF8をUTF16と誤解することがある問題を修正
	・その他，エディタ機能急造のため，対応すべき，整理する点あるが，未対応
・ 1.4.1 2010/05/26
	・READMEの修正
・ 1.4.1 2010/04/16
	・ FrameInitializingの順序修正（入れ替わっていたので，以前のinitialize->showの順に戻した）
	・ Eclipseプラグインから呼び出し時， DoExitでSystem.exit()を呼び出し，Eclipse毎終了しまう不具合を修正
・ 1.4.0 2010/04/15
	・ Editor機能を追加
	・ EclipseプラグインをAWT呼び出し方式に変更
・ 1.3.11 2009/04/02
	・ ToAll時のclog吐き出しに微調整を加えたもの
	・ build.xmlを作成した．
・ 1.3.10 2009/04/01
	・ ToPNGAll, ToSVGAllをオフィシャルに作成
	・ ToSVG時, 何もないIdのモジュールに_defaultが付加されていたのをやめるよう修正（多少アドホックに修正）
・ 1.3.9 2009/04/01
	・ application.cuiの周りをリファクタリング　Exportingコードをフレームワーク化し冗長なコードを削除
	・ ToPNG, ToSVGをオフィシャルに作成
	・ binパッケージを復活
・ 1.3.8 2009/09/10
	・ 印刷時，モジュールmainが二つ印刷されてしまう問題のバグ修正（単純に2度印刷されるようになっていたのでコメントアウト）
	・ Genericsコードに対応し，warningを消した
・ 1.3.7 2009/08/17
	・ 印刷や画像はきだし時，非表示類が対応していなかったのに対応．（大岩先生のリクエストに対応）
・ 1.3.6 2009/08/12
	・ 印刷や画像はきだし時，倍率やレベル表示が対応していなかったのに対応．（大岩先生のリクエストに対応）
	・ グローバルプロパティに対応．（この作業の際，モジュール変更時にフォントボックスが切り替わらない問題に対応して実装）
	・ 1.3.3の仕様変更でいつの間にかフォントが変わらなくなっていた．
	・ Contextのフォントをproperty変更時に変えてやることで解決.ContextとPropertyの間のコードが汚いので整理が必要．
・ 1.3.5 2009/05/11
	・ 文字コードを自動判定とし，UTF8を読み込み可能にする
	・ ライセンス問題をクリアして，SVG機能を復活．今後はExport JarExecutableではき出すこと．
	・ オブプロサンプルズに必要だったので，EPS機能を暫定追加（SVG吐き出しで同時にはかれる）
・ 1.3.4 2009/05/08
	・ Debugモードにsetterを追加
・ 1.3.3 2009/05/08 For EclipsePlugin
	・ Context#Debugモード追加
	・ HRenderingContextをAWT,SWTに分離
・ 1.3.2 2008/10/14 バグ修正
	・ HModule#getId()でデータだけのデフォルトモジュールではIDが付加されず，再読み込みでエラーが出る問題を修正
	・ HCommandAnalyzerFactory#showNoDeclarationCommandMessage()で例外が起こるときがある場合（再現できていない）の応急処置
 ・ 1.3.1 2008/10/10 バグ修正
	・ DebugConsole機能がいつの間にか削除されていた．HViewerFrameに一行追加で機能復活
	・ SVG出力機能でエラー（BatikーLib読み込み方式変更によるので，1.2.3bの変更によりライブラリが読めなくなったことが原因）
	・ ↑ライブラリが読み込めない場合，SVG出力機能を無効にするよう修正
・ 1.3.0 2008/10/04 印刷，描画の計算をかなり修正
	・ FontとWidth計算に誤りがあったのを修正．RenderingContextとGraphicsの調整をした．
 	・ layoutまえにsetGraphicsでgをセットすること
	・ Fontを選択できるようにした． 印刷時も反映される．
	・ 印刷の方法を修正 API2で行うようにした．
	・ 横長になる場合に切れるバグを修正．横長になった場合印刷領域との比率を測り，適切なスケールの調整をすることで対応
・ 1.2.6 2008/10/03 描画の方式をかなり修正
	・ HDataEnvironment プロセスのAbsoluteパスにあわせる方式だと2回目以降ヘッダーも含まれてしまう問題を修正
	・ Builderのstatic方式に伴う2回目以降更新のバグを修正．static方式をやめ，getModule()を追加．
	・ 行番号をマイナス領域に書かないようにする．これで，x,yマージンの考え方が明確になった
・ 1.2.5a 2008/10/02 要望のあて先をgackt->macchanに修正	
・ 1.2.4 2008/10/02 JRE1.5でスプラッシュがこけるのを修正
・ 1.2.3b 2008/10/02 HCPViewerLauncher系由の起動に不具合有り，直接起動に変更
・ 1.2.3a 2008/10/02 ヘルプ画面に使用中のJavaバージョンを表記（Debug用）
・ 1.2.2 2008/06/27 JDK1.6のスプラッシュ機能に対応．
	・ Manifest.mfも変更
	・ インストーラはinstallsheildから卒業
・ 1.2.1 2008/05/17 コマンドライン機能を追加(application.commandsパッケージ）
	・ とりあえずPNG出力のみ
	・ モジュール名が重複した場合<>記号が使われるが，Vistaではファイル名として使えないので，exportするときに変換するようバグ修正
・ 1.2.0 2007/06/08 Eclipse用HCPViewerPlug-in用にリファクタリング
	・ 古いバージョンのクラスを削除（testパッケージ）
	・ HViewerのpaint部分をpublic化してSWTからも簡単に使えるようにする
・ 1.1.6 2006/04/24 日本人が使う英語版Windowsに対応
	・ オブプロで英語版Windowsで日本語がうまく読み込めないので，JISAutoDetectで読み込むようにしました．
・ 1.1.5 2006/04/23 印刷等修正
	・ レベル表示変更後の印刷（つまりレベル指定印刷）ができなかった．
	・ HViewPropertyを反映するようPrintAction修正
	・ 久しぶりにソースをいじったが，HViewPropertyとHRenderingContextの関係が未整理である
	・ ノートのレベル表示が1段階おかしかったので修正(HVModule#buildNotes())
	・ 2枚以上の印刷に対応
・ 1.1.4 2005/04/17 データ修正
	・ ＨＶ関連のクラス名をリファクタリング
	・ 繰り返しデータを扱えるようにした．
	・ 一応select, condも導入したが，特にcondの出力はおかしい
・ 1.1.3 2005/04/17 ラウンチャー修正
	・ Ｍａｃでラウンチャーが起動せず．
	・ File.pathSeparator導入
・ 1.1.2 2005/04/17 ラウンチャー追加
	・ application.launcher.HCPViewerLauncherクラスを追加
・ 1.1.1 2005/04/16 バグ修正
	・ 再読み込みができないバグ修正
	・ エラーチェックがレベルボタンを押したとき聞かなくなるのを修正
・ 1.1.0 2005/04/07 初期リリース
