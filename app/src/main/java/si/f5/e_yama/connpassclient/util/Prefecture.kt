package si.f5.e_yama.connpassclient.util

/**
 * 都道府県列挙クラス
 *
 * Copyright (c) 2016 javasampleokiba
 *
 * This source code is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 *
 *      *** converted from java ***
 */
enum class Prefecture(val code: Int, val text: String, val area: Area) {
    HOKKAIDO(1, "北海道", Area.HOKKAIDO),
    AOMORI(2, "青森", Area.TOHOKU),
    IWATE(3, "岩手", Area.TOHOKU),
    MIYAGI(4, "宮城", Area.TOHOKU),
    AKITA(5, "秋田", Area.TOHOKU),
    YAMAGATA(6, "山形", Area.TOHOKU),
    FUKUSHIMA(7, "福島", Area.TOHOKU),
    IBARAKI(8, "茨城", Area.KANTO),
    TOCHIGI(9, "栃木", Area.KANTO),
    GUNMA(10, "群馬", Area.KANTO),
    SAITAMA(11, "埼玉", Area.KANTO),
    CHIBA(12, "千葉", Area.KANTO),
    TOKYO(13, "東京", Area.KANTO),
    KANAGAWA(14, "神奈川", Area.KANTO),
    NIIGATA(15, "新潟", Area.CHUBU),
    TOYAMA(16, "富山", Area.CHUBU),
    ISHIKAWA(17, "石川", Area.CHUBU),
    FUKUI(18, "福井", Area.CHUBU),
    YAMANASHI(19, "山梨", Area.CHUBU),
    NAGANO(20, "長野", Area.CHUBU),
    GIFU(21, "岐阜", Area.CHUBU),
    SHIZUOKA(22, "静岡", Area.CHUBU),
    AICHI(23, "愛知", Area.CHUBU),
    MIE(24, "三重", Area.KINKI),
    SHIGA(25, "滋賀", Area.KINKI),
    KYOTO(26, "京都", Area.KINKI),
    OSAKA(27, "大阪", Area.KINKI),
    HYOGO(28, "兵庫", Area.KINKI),
    NARA(29, "奈良", Area.KINKI),
    WAKAYAMA(30, "和歌山", Area.KINKI),
    TOTTORI(31, "鳥取", Area.CHUGOKU),
    SHIMANE(32, "島根", Area.CHUGOKU),
    OKAYAMA(33, "岡山", Area.CHUGOKU),
    HIROSHIMA(34, "広島", Area.CHUGOKU),
    YAMAGUCHI(35, "山口", Area.CHUGOKU),
    TOKUSHIMA(36, "徳島", Area.SHIKOKU),
    KAGAWA(37, "香川", Area.SHIKOKU),
    EHIME(38, "愛媛", Area.SHIKOKU),
    KOCHI(39, "高知", Area.SHIKOKU),
    FUKUOKA(40, "福岡", Area.KYUSHU),
    SAGA(41, "佐賀", Area.KYUSHU),
    NAGASAKI(42, "長崎", Area.KYUSHU),
    KUMAMOTO(43, "熊本", Area.KYUSHU),
    OITA(44, "大分", Area.KYUSHU),
    MIYAZAKI(45, "宮崎", Area.KYUSHU),
    KAGOSHIMA(46, "鹿児島", Area.KYUSHU),
    OKINAWA(47, "沖縄", Area.KYUSHU),
    OTHERS(0, "オンライン", Area.OTHERS);

    /**
     * 都道府県名を取得します。
     * "都"、"府"、"県"を含みます。
     *
     * @return 都道府県名
     */
    val full: String
        get() = when (this) {
            HOKKAIDO, OTHERS -> text
            TOKYO -> text + "都"
            KYOTO, OSAKA -> text + "府"
            else -> text + "県"
        }

    companion object {
        /**
         * 指定されたコードのPrefectureオブジェクトを取得します。
         * 存在しない場合はnullを返します。
         *
         * @param code    都道府県コード
         * @return Prefectureオブジェクト
         */
        fun getByCode(code: Int): Prefecture? {
            for (p in values()) {
                if (p.code == code) return p
            }
            return null
        }

        /**
         * 指定された都道府県名（"都"、"府"、"県"は含まない）のPrefectureオブジェクトを取得します。
         * 存在しない場合はnullを返します。
         *
         * @param text    都道府県名
         * @return Prefectureオブジェクト
         */
        fun getByText(text: String): Prefecture? {
            for (p in values()) {
                if (p.text == text) return p
            }
            return null
        }

        /**
         * 指定された都道府県名（"都"、"府"、"県"を含む）のPrefectureオブジェクトを取得します。
         * 存在しない場合はnullを返します。
         *
         * @param full    都道府県名
         * @return Prefectureオブジェクト
         */
        fun getByFullName(full: String): Prefecture? {
            for (p in values()) {
                if (p.full == full) return p
            }
            return null
        }

        fun getByArea(area: Area): Array<Prefecture> {
            val list = arrayListOf<Prefecture>()
            values().forEach {
                if (it.area == area)
                    list.add(it)
            }

            return list.toArray(arrayOf())
        }
    }

    enum class Area(val text: String) {
        HOKKAIDO("北海道"), TOHOKU("東北"), KANTO("関東"), CHUBU("中部"), KINKI("近畿"), CHUGOKU("中国"), SHIKOKU("四国"), KYUSHU("九州"), OTHERS("その他")
    }
}