package POJO;

import Annotation.DBFClass;

import java.util.Date;
import java.io.Serializable;

/**
 * 招生-录取表(EnrRecruit)实体类
 *
 * @author Tony Dang
 * @since 2021-12-24 15:59:37
 */
@DBFClass
public class EnrRecruit implements Serializable {
    private static final long serialVersionUID = -82812114041215311L;
    /**
     * 主键
     */
    private String id;
    /**
     * 层次代码
     */
    private String ccm;
    /**
     * 层次名称
     */
    private String ccmc;
    /**
     * 科类代码
     */
    private String klm;
    /**
     * 科类名称
     */
    private String klmc;
    /**
     * 科类修正
     */
    private String klmcxz;
    /**
     * 省份(省份全称)
     */
    private String syd;
    /**
     * 生源地码(地区代码)
     */
    private String sydm;
    /**
     * 考区码(取生源地码前两位)
     */
    private String kqm;
    /**
     * 生源地名称(地区名称)
     */
    private String sydmc;
    /**
     * 考生号
     */
    private String ksh;
    /**
     * 姓名
     */
    private String xm;
    /**
     * 学号
     */
    private String xh;
    /**
     * 准考证号
     */
    private String zkzh;
    /**
     * 身份证件类型名称
     */
    private String sfzjlxmc;
    /**
     * 身份证件类型码
     */
    private String sfzjlxm;
    /**
     * 身份证件号
     */
    private String sfzjh;
    /**
     * 学籍号
     */
    private String xjh;
    /**
     * 性别码
     */
    private String xbm;
    /**
     * 性别名称
     */
    private String xbmc;
    /**
     * 照片(附件id)
     */
    private String zp;
    /**
     * 出生日期
     */
    private Long csrq;
    /**
     * 批次代码
     */
    private String pcm;
    /**
     * 批次名称
     */
    private String pcmc;
    /**
     * 批次修正
     */
    private String pcmcxz;
    /**
     * 计划性质代码
     */
    private String jhxzm;
    /**
     * 计划性质名称
     */
    private String jhxzmc;
    /**
     * 投档单位代码
     */
    private String tddwm;
    /**
     * 投档单位名称(当前投档单位)
     */
    private String tddwmc;
    /**
     * 考生状态名称
     */
    private String ksztmc;
    /**
     * 考生状态代码
     */
    private String ksztm;
    /**
     * 考生资格代码
     */
    private String kszgm;
    /**
     * 考生资格名称
     */
    private String kszgmc;
    /**
     * 专业代码
     */
    private String zydm;
    /**
     * 专业代码修正
     */
    private String zydmxz;
    /**
     * 专业名称
     */
    private String zymc;
    /**
     * 专业名称修正
     */
    private String zymcxz;
    /**
     * 录取专业代码
     */
    private String lqzym;
    /**
     * 录取专业名称
     */
    private String lqzy;
    /**
     * 录取省市专业码
     */
    private String lqsszym;
    /**
     * 招考方向
     */
    private String zkfx;
    /**
     * 外语听力
     */
    private String tl;
    /**
     * 考生类别代码
     */
    private String kslbm;
    /**
     * 考生类别名称
     */
    private String kslbmc;
    /**
     * 民族代码
     */
    private String mzm;
    /**
     * 民族名称
     */
    private String mzmc;
    /**
     * 政治面貌代码
     */
    private String zzmmm;
    /**
     * 政治面貌名称
     */
    private String zzmmmc;
    /**
     * 毕业学校代码(毕业中学号)
     */
    private String byzxh;
    /**
     * 毕业学校名称
     */
    private String byzxmc;
    /**
     * 毕业中学邮政编码
     */
    private String byzxyzbm;
    /**
     * 毕业类别代码
     */
    private String bylbm;
    /**
     * 毕业类别名称
     */
    private String bylbmc;
    /**
     * 毕业类别名称修正
     */
    private String bylbmcxz;
    /**
     * 考试类型代码
     */
    private String kslxm;
    /**
     * 考试类型名称
     */
    private String kslxmc;
    /**
     * 考生特征代码
     */
    private String kstzm;
    /**
     * 考生特征名称
     */
    private String kstzmc;
    /**
     * 残障类别代码
     */
    private String czlbm;
    /**
     * 残障类别名称
     */
    private String czlbmc;
    /**
     * 户籍类别代码
     */
    private String hjlbm;
    /**
     * 户籍类别名称
     */
    private String hjlbmc;
    /**
     * 户口所在地代码
     */
    private String hkszdm;
    /**
     * 户口所在地
     */
    private String hkszd;
    /**
     * 家庭地址(通信地址)
     */
    private String txdz;
    /**
     * 邮政编码
     */
    private String yzbm;
    /**
     * 收件人
     */
    private String sjr;
    /**
     * 联系电话
     */
    private String dh;
    /**
     * 联系手机
     */
    private String lxsj;
    /**
     * 是否报到，0否1是
     */
    private Integer registerFlag;
    /**
     * 报到备注
     */
    private String bz;
    /**
     * 有何特长
     */
    private String kstc;
    /**
     * 入学年份
     */
    private String rxnf;
    /**
     * 应试卷种代码
     */
    private String ysjzm;
    /**
     * 外语语种代码(应试外国语种码)
     */
    private String yswgyzm;
    /**
     * 外语语种名称
     */
    private String wyyzmc;
    /**
     * 外语口试名称
     */
    private String wyksmc;
    /**
     * 外语口试代码
     */
    private String wyksm;
    /**
     * 外语听力代码
     */
    private String wytlm;
    /**
     * 户口性质代码
     */
    private String hkxzm;
    /**
     * 户口性质名称
     */
    private String hkxzmc;
    /**
     * 学制
     */
    private String xz;
    /**
     * 学制代码
     */
    private String xzm;
    /**
     * 选考科目代码
     */
    private String xkkmm;
    /**
     * 术科科目代码
     */
    private String skkmm;
    /**
     * 选考科目
     */
    private String xkkm;
    /**
     * 术科科目
     */
    private String skkm;
    /**
     * 会考考号
     */
    private String hkkh;
    /**
     * 何时何地受过何种奖励或处分(奖励与惩处)
     */
    private String jlycc;
    /**
     * 思想品德考核意见
     */
    private String sxpdkhyj;
    /**
     * 退档原因
     */
    private String tdyy;
    /**
     * 退档原因代码
     */
    private String tdyym;
    /**
     * 成绩
     */
    private Double cj;
    /**
     * 是否允许专业调剂，0否1是
     */
    private String zytj;
    /**
     * 是否允许定向调剂，0否1是
     */
    private String dxtj;
    /**
     * 志愿特征代码
     */
    private String zytzm;
    /**
     * 志愿特征名称
     */
    private String zytzmc;
    /**
     * 专业类别代码
     */
    private String zylbm;
    /**
     * 专业类别名称
     */
    private String zylbmc;
    /**
     * 招考类型代码(录取类别码)
     */
    private String zklxm;
    /**
     * 招考类型名称(录取类别名称)
     */
    private String zklxmc;
    /**
     * 计划类别名称
     */
    private String jhlbmc;
    /**
     * 计划类别代码
     */
    private String jhlbm;
    /**
     * 是否有报名表，0否1是
     */
    private Integer bmb;
    /**
     * 是否有成绩志愿表，0否1是
     */
    private Integer cjzyb;
    /**
     * 是否有体检表，0否1是
     */
    private Integer tjb;
    /**
     * 通知书号
     */
    private String tzsh;
    /**
     * 录取院系所号
     */
    private String lqyxsh;
    /**
     * 是否公费医疗，0否1是
     */
    private String sfgfyl;
    /**
     * 班号
     */
    private String bh;
    /**
     * 本校专业号
     */
    private String bxzyh;
    /**
     * 是否导入迎新系统，0否1是
     */
    private Integer yxFlag;
    /**
     * 缴费状态(0未缴费，1已缴费)
     */
    private Integer payFlag;
    /**
     * 分配宿舍状态(0未分配，1已分配)
     */
    private Integer dormFlag;
    /**
     * 现场确认报到（0：否，1：是）
     */
    private Integer affirmFlag;
    /**
     * 确认报到时间
     */
    private Long affirmTime;
    /**
     * 审核状态（0：未审核，1：待审核，2：通过，3：通过）
     */
    private Integer checkFlag;
    /**
     * 采集状态（0：否，1：是）
     */
    private Integer gatherFlag;
    /**
     * 采集时间
     */
    private Long gatherTime;
    /**
     * 删除标识0未删除1删除默认值0
     */
    private Integer deleteFlag;
    /**
     * 创建者主键
     */
    private String createUserId;
    /**
     * 更新者主键
     */
    private String updateUserId;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 更新时间
     */
    private Long updateTime;
    /**
     * 创建日期
     */
    private Date createDay;
    /**
     * 更新日期
     */
    private Date updateDay;
    /**
     * 数据来源，0数据库文件导入，1模板导入，2dbf文件
     */
    private String sourceType;
    /**
     * 备用字段1
     */
    private String temp1;
    /**
     * 备用字段2
     */
    private String temp2;
    /**
     * 备用字段3
     */
    private String temp3;
    /**
     * 备用字段4
     */
    private String temp4;
    /**
     * 备用字段5
     */
    private String temp5;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCcm() {
        return ccm;
    }

    public void setCcm(String ccm) {
        this.ccm = ccm;
    }

    public String getCcmc() {
        return ccmc;
    }

    public void setCcmc(String ccmc) {
        this.ccmc = ccmc;
    }

    public String getKlm() {
        return klm;
    }

    public void setKlm(String klm) {
        this.klm = klm;
    }

    public String getKlmc() {
        return klmc;
    }

    public void setKlmc(String klmc) {
        this.klmc = klmc;
    }

    public String getKlmcxz() {
        return klmcxz;
    }

    public void setKlmcxz(String klmcxz) {
        this.klmcxz = klmcxz;
    }

    public String getSyd() {
        return syd;
    }

    public void setSyd(String syd) {
        this.syd = syd;
    }

    public String getSydm() {
        return sydm;
    }

    public void setSydm(String sydm) {
        this.sydm = sydm;
    }

    public String getKqm() {
        return kqm;
    }

    public void setKqm(String kqm) {
        this.kqm = kqm;
    }

    public String getSydmc() {
        return sydmc;
    }

    public void setSydmc(String sydmc) {
        this.sydmc = sydmc;
    }

    public String getKsh() {
        return ksh;
    }

    public void setKsh(String ksh) {
        this.ksh = ksh;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getZkzh() {
        return zkzh;
    }

    public void setZkzh(String zkzh) {
        this.zkzh = zkzh;
    }

    public String getSfzjlxmc() {
        return sfzjlxmc;
    }

    public void setSfzjlxmc(String sfzjlxmc) {
        this.sfzjlxmc = sfzjlxmc;
    }

    public String getSfzjlxm() {
        return sfzjlxm;
    }

    public void setSfzjlxm(String sfzjlxm) {
        this.sfzjlxm = sfzjlxm;
    }

    public String getSfzjh() {
        return sfzjh;
    }

    public void setSfzjh(String sfzjh) {
        this.sfzjh = sfzjh;
    }

    public String getXjh() {
        return xjh;
    }

    public void setXjh(String xjh) {
        this.xjh = xjh;
    }

    public String getXbm() {
        return xbm;
    }

    public void setXbm(String xbm) {
        this.xbm = xbm;
    }

    public String getXbmc() {
        return xbmc;
    }

    public void setXbmc(String xbmc) {
        this.xbmc = xbmc;
    }

    public String getZp() {
        return zp;
    }

    public void setZp(String zp) {
        this.zp = zp;
    }

    public Long getCsrq() {
        return csrq;
    }

    public void setCsrq(Long csrq) {
        this.csrq = csrq;
    }

    public String getPcm() {
        return pcm;
    }

    public void setPcm(String pcm) {
        this.pcm = pcm;
    }

    public String getPcmc() {
        return pcmc;
    }

    public void setPcmc(String pcmc) {
        this.pcmc = pcmc;
    }

    public String getPcmcxz() {
        return pcmcxz;
    }

    public void setPcmcxz(String pcmcxz) {
        this.pcmcxz = pcmcxz;
    }

    public String getJhxzm() {
        return jhxzm;
    }

    public void setJhxzm(String jhxzm) {
        this.jhxzm = jhxzm;
    }

    public String getJhxzmc() {
        return jhxzmc;
    }

    public void setJhxzmc(String jhxzmc) {
        this.jhxzmc = jhxzmc;
    }

    public String getTddwm() {
        return tddwm;
    }

    public void setTddwm(String tddwm) {
        this.tddwm = tddwm;
    }

    public String getTddwmc() {
        return tddwmc;
    }

    public void setTddwmc(String tddwmc) {
        this.tddwmc = tddwmc;
    }

    public String getKsztmc() {
        return ksztmc;
    }

    public void setKsztmc(String ksztmc) {
        this.ksztmc = ksztmc;
    }

    public String getKsztm() {
        return ksztm;
    }

    public void setKsztm(String ksztm) {
        this.ksztm = ksztm;
    }

    public String getKszgm() {
        return kszgm;
    }

    public void setKszgm(String kszgm) {
        this.kszgm = kszgm;
    }

    public String getKszgmc() {
        return kszgmc;
    }

    public void setKszgmc(String kszgmc) {
        this.kszgmc = kszgmc;
    }

    public String getZydm() {
        return zydm;
    }

    public void setZydm(String zydm) {
        this.zydm = zydm;
    }

    public String getZydmxz() {
        return zydmxz;
    }

    public void setZydmxz(String zydmxz) {
        this.zydmxz = zydmxz;
    }

    public String getZymc() {
        return zymc;
    }

    public void setZymc(String zymc) {
        this.zymc = zymc;
    }

    public String getZymcxz() {
        return zymcxz;
    }

    public void setZymcxz(String zymcxz) {
        this.zymcxz = zymcxz;
    }

    public String getLqzym() {
        return lqzym;
    }

    public void setLqzym(String lqzym) {
        this.lqzym = lqzym;
    }

    public String getLqzy() {
        return lqzy;
    }

    public void setLqzy(String lqzy) {
        this.lqzy = lqzy;
    }

    public String getLqsszym() {
        return lqsszym;
    }

    public void setLqsszym(String lqsszym) {
        this.lqsszym = lqsszym;
    }

    public String getZkfx() {
        return zkfx;
    }

    public void setZkfx(String zkfx) {
        this.zkfx = zkfx;
    }

    public String getTl() {
        return tl;
    }

    public void setTl(String tl) {
        this.tl = tl;
    }

    public String getKslbm() {
        return kslbm;
    }

    public void setKslbm(String kslbm) {
        this.kslbm = kslbm;
    }

    public String getKslbmc() {
        return kslbmc;
    }

    public void setKslbmc(String kslbmc) {
        this.kslbmc = kslbmc;
    }

    public String getMzm() {
        return mzm;
    }

    public void setMzm(String mzm) {
        this.mzm = mzm;
    }

    public String getMzmc() {
        return mzmc;
    }

    public void setMzmc(String mzmc) {
        this.mzmc = mzmc;
    }

    public String getZzmmm() {
        return zzmmm;
    }

    public void setZzmmm(String zzmmm) {
        this.zzmmm = zzmmm;
    }

    public String getZzmmmc() {
        return zzmmmc;
    }

    public void setZzmmmc(String zzmmmc) {
        this.zzmmmc = zzmmmc;
    }

    public String getByzxh() {
        return byzxh;
    }

    public void setByzxh(String byzxh) {
        this.byzxh = byzxh;
    }

    public String getByzxmc() {
        return byzxmc;
    }

    public void setByzxmc(String byzxmc) {
        this.byzxmc = byzxmc;
    }

    public String getByzxyzbm() {
        return byzxyzbm;
    }

    public void setByzxyzbm(String byzxyzbm) {
        this.byzxyzbm = byzxyzbm;
    }

    public String getBylbm() {
        return bylbm;
    }

    public void setBylbm(String bylbm) {
        this.bylbm = bylbm;
    }

    public String getBylbmc() {
        return bylbmc;
    }

    public void setBylbmc(String bylbmc) {
        this.bylbmc = bylbmc;
    }

    public String getBylbmcxz() {
        return bylbmcxz;
    }

    public void setBylbmcxz(String bylbmcxz) {
        this.bylbmcxz = bylbmcxz;
    }

    public String getKslxm() {
        return kslxm;
    }

    public void setKslxm(String kslxm) {
        this.kslxm = kslxm;
    }

    public String getKslxmc() {
        return kslxmc;
    }

    public void setKslxmc(String kslxmc) {
        this.kslxmc = kslxmc;
    }

    public String getKstzm() {
        return kstzm;
    }

    public void setKstzm(String kstzm) {
        this.kstzm = kstzm;
    }

    public String getKstzmc() {
        return kstzmc;
    }

    public void setKstzmc(String kstzmc) {
        this.kstzmc = kstzmc;
    }

    public String getCzlbm() {
        return czlbm;
    }

    public void setCzlbm(String czlbm) {
        this.czlbm = czlbm;
    }

    public String getCzlbmc() {
        return czlbmc;
    }

    public void setCzlbmc(String czlbmc) {
        this.czlbmc = czlbmc;
    }

    public String getHjlbm() {
        return hjlbm;
    }

    public void setHjlbm(String hjlbm) {
        this.hjlbm = hjlbm;
    }

    public String getHjlbmc() {
        return hjlbmc;
    }

    public void setHjlbmc(String hjlbmc) {
        this.hjlbmc = hjlbmc;
    }

    public String getHkszdm() {
        return hkszdm;
    }

    public void setHkszdm(String hkszdm) {
        this.hkszdm = hkszdm;
    }

    public String getHkszd() {
        return hkszd;
    }

    public void setHkszd(String hkszd) {
        this.hkszd = hkszd;
    }

    public String getTxdz() {
        return txdz;
    }

    public void setTxdz(String txdz) {
        this.txdz = txdz;
    }

    public String getYzbm() {
        return yzbm;
    }

    public void setYzbm(String yzbm) {
        this.yzbm = yzbm;
    }

    public String getSjr() {
        return sjr;
    }

    public void setSjr(String sjr) {
        this.sjr = sjr;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public String getLxsj() {
        return lxsj;
    }

    public void setLxsj(String lxsj) {
        this.lxsj = lxsj;
    }

    public Integer getRegisterFlag() {
        return registerFlag;
    }

    public void setRegisterFlag(Integer registerFlag) {
        this.registerFlag = registerFlag;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getKstc() {
        return kstc;
    }

    public void setKstc(String kstc) {
        this.kstc = kstc;
    }

    public String getRxnf() {
        return rxnf;
    }

    public void setRxnf(String rxnf) {
        this.rxnf = rxnf;
    }

    public String getYsjzm() {
        return ysjzm;
    }

    public void setYsjzm(String ysjzm) {
        this.ysjzm = ysjzm;
    }

    public String getYswgyzm() {
        return yswgyzm;
    }

    public void setYswgyzm(String yswgyzm) {
        this.yswgyzm = yswgyzm;
    }

    public String getWyyzmc() {
        return wyyzmc;
    }

    public void setWyyzmc(String wyyzmc) {
        this.wyyzmc = wyyzmc;
    }

    public String getWyksmc() {
        return wyksmc;
    }

    public void setWyksmc(String wyksmc) {
        this.wyksmc = wyksmc;
    }

    public String getWyksm() {
        return wyksm;
    }

    public void setWyksm(String wyksm) {
        this.wyksm = wyksm;
    }

    public String getWytlm() {
        return wytlm;
    }

    public void setWytlm(String wytlm) {
        this.wytlm = wytlm;
    }

    public String getHkxzm() {
        return hkxzm;
    }

    public void setHkxzm(String hkxzm) {
        this.hkxzm = hkxzm;
    }

    public String getHkxzmc() {
        return hkxzmc;
    }

    public void setHkxzmc(String hkxzmc) {
        this.hkxzmc = hkxzmc;
    }

    public String getXz() {
        return xz;
    }

    public void setXz(String xz) {
        this.xz = xz;
    }

    public String getXzm() {
        return xzm;
    }

    public void setXzm(String xzm) {
        this.xzm = xzm;
    }

    public String getXkkmm() {
        return xkkmm;
    }

    public void setXkkmm(String xkkmm) {
        this.xkkmm = xkkmm;
    }

    public String getSkkmm() {
        return skkmm;
    }

    public void setSkkmm(String skkmm) {
        this.skkmm = skkmm;
    }

    public String getXkkm() {
        return xkkm;
    }

    public void setXkkm(String xkkm) {
        this.xkkm = xkkm;
    }

    public String getSkkm() {
        return skkm;
    }

    public void setSkkm(String skkm) {
        this.skkm = skkm;
    }

    public String getHkkh() {
        return hkkh;
    }

    public void setHkkh(String hkkh) {
        this.hkkh = hkkh;
    }

    public String getJlycc() {
        return jlycc;
    }

    public void setJlycc(String jlycc) {
        this.jlycc = jlycc;
    }

    public String getSxpdkhyj() {
        return sxpdkhyj;
    }

    public void setSxpdkhyj(String sxpdkhyj) {
        this.sxpdkhyj = sxpdkhyj;
    }

    public String getTdyy() {
        return tdyy;
    }

    public void setTdyy(String tdyy) {
        this.tdyy = tdyy;
    }

    public String getTdyym() {
        return tdyym;
    }

    public void setTdyym(String tdyym) {
        this.tdyym = tdyym;
    }

    public Double getCj() {
        return cj;
    }

    public void setCj(Double cj) {
        this.cj = cj;
    }

    public String getZytj() {
        return zytj;
    }

    public void setZytj(String zytj) {
        this.zytj = zytj;
    }

    public String getDxtj() {
        return dxtj;
    }

    public void setDxtj(String dxtj) {
        this.dxtj = dxtj;
    }

    public String getZytzm() {
        return zytzm;
    }

    public void setZytzm(String zytzm) {
        this.zytzm = zytzm;
    }

    public String getZytzmc() {
        return zytzmc;
    }

    public void setZytzmc(String zytzmc) {
        this.zytzmc = zytzmc;
    }

    public String getZylbm() {
        return zylbm;
    }

    public void setZylbm(String zylbm) {
        this.zylbm = zylbm;
    }

    public String getZylbmc() {
        return zylbmc;
    }

    public void setZylbmc(String zylbmc) {
        this.zylbmc = zylbmc;
    }

    public String getZklxm() {
        return zklxm;
    }

    public void setZklxm(String zklxm) {
        this.zklxm = zklxm;
    }

    public String getZklxmc() {
        return zklxmc;
    }

    public void setZklxmc(String zklxmc) {
        this.zklxmc = zklxmc;
    }

    public String getJhlbmc() {
        return jhlbmc;
    }

    public void setJhlbmc(String jhlbmc) {
        this.jhlbmc = jhlbmc;
    }

    public String getJhlbm() {
        return jhlbm;
    }

    public void setJhlbm(String jhlbm) {
        this.jhlbm = jhlbm;
    }

    public Integer getBmb() {
        return bmb;
    }

    public void setBmb(Integer bmb) {
        this.bmb = bmb;
    }

    public Integer getCjzyb() {
        return cjzyb;
    }

    public void setCjzyb(Integer cjzyb) {
        this.cjzyb = cjzyb;
    }

    public Integer getTjb() {
        return tjb;
    }

    public void setTjb(Integer tjb) {
        this.tjb = tjb;
    }

    public String getTzsh() {
        return tzsh;
    }

    public void setTzsh(String tzsh) {
        this.tzsh = tzsh;
    }

    public String getLqyxsh() {
        return lqyxsh;
    }

    public void setLqyxsh(String lqyxsh) {
        this.lqyxsh = lqyxsh;
    }

    public String getSfgfyl() {
        return sfgfyl;
    }

    public void setSfgfyl(String sfgfyl) {
        this.sfgfyl = sfgfyl;
    }

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public String getBxzyh() {
        return bxzyh;
    }

    public void setBxzyh(String bxzyh) {
        this.bxzyh = bxzyh;
    }

    public Integer getYxFlag() {
        return yxFlag;
    }

    public void setYxFlag(Integer yxFlag) {
        this.yxFlag = yxFlag;
    }

    public Integer getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(Integer payFlag) {
        this.payFlag = payFlag;
    }

    public Integer getDormFlag() {
        return dormFlag;
    }

    public void setDormFlag(Integer dormFlag) {
        this.dormFlag = dormFlag;
    }

    public Integer getAffirmFlag() {
        return affirmFlag;
    }

    public void setAffirmFlag(Integer affirmFlag) {
        this.affirmFlag = affirmFlag;
    }

    public Long getAffirmTime() {
        return affirmTime;
    }

    public void setAffirmTime(Long affirmTime) {
        this.affirmTime = affirmTime;
    }

    public Integer getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Integer checkFlag) {
        this.checkFlag = checkFlag;
    }

    public Integer getGatherFlag() {
        return gatherFlag;
    }

    public void setGatherFlag(Integer gatherFlag) {
        this.gatherFlag = gatherFlag;
    }

    public Long getGatherTime() {
        return gatherTime;
    }

    public void setGatherTime(Long gatherTime) {
        this.gatherTime = gatherTime;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateDay() {
        return createDay;
    }

    public void setCreateDay(Date createDay) {
        this.createDay = createDay;
    }

    public Date getUpdateDay() {
        return updateDay;
    }

    public void setUpdateDay(Date updateDay) {
        this.updateDay = updateDay;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getTemp1() {
        return temp1;
    }

    public void setTemp1(String temp1) {
        this.temp1 = temp1;
    }

    public String getTemp2() {
        return temp2;
    }

    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }

    public String getTemp3() {
        return temp3;
    }

    public void setTemp3(String temp3) {
        this.temp3 = temp3;
    }

    public String getTemp4() {
        return temp4;
    }

    public void setTemp4(String temp4) {
        this.temp4 = temp4;
    }

    public String getTemp5() {
        return temp5;
    }

    public void setTemp5(String temp5) {
        this.temp5 = temp5;
    }

}

