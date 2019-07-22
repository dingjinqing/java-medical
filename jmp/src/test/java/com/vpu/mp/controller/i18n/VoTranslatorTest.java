package com.vpu.mp.controller.i18n;

import com.vpu.mp.controller.VoTranslator;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 自动国际化翻译器测试
 *
 * @author 郑保乐
 */
public class VoTranslatorTest {

    private PureVo[] voes = new PureVo[5];

    private ListVo listVo = new ListVo();

    private VoTranslator translator;

    private HttpServletRequest reqMock;

    private static final String HEADER_LANG = "V-Lang";

    @Before
    public void setUp() {
        voes[0] = new PureVo("food");
        voes[1] = new PureVo("digital");
        voes[2] = new PureVo("clean");
        voes[3] = new PureVo("cook");
        voes[4] = new PureVo("drink");
        reqMock = mock(HttpServletRequest.class);
        when(reqMock.getHeader(HEADER_LANG)).thenReturn("zh_CN");
        translator = new VoTranslator(reqMock);
    }

    /**
     * 测试请求头中不包含语言字段时的翻译
     */
    @Test
    public void assertPropertyTranslatedWhenDefaultLanguage() {
        when(reqMock.getHeader(HEADER_LANG)).thenReturn(null);
        translateCategoryArray();
    }

    /**
     * 测试请求头中包含语言字段时的翻译
     */
    @Test
    public void assertPropertyTranslatedZhCN() {
        translateCategoryArray();
    }

    /**
     * 翻译数组中的对象
     */
    private void translateCategoryArray() {
        for (PureVo vo : voes) {
            translator.translateFields(vo);
        }
        assertEquals(voes[0].getName(), "食品");
        assertEquals(voes[1].getName(), "数码");
        assertEquals(voes[2].getName(), "清洁");
        assertEquals(voes[3].getName(), "厨具");
        assertEquals(voes[4].getName(), "饮品");
    }

    /**
     * 测试出参类中包含 {@code List<String>} 属性的翻译
     */
    @Test
    public void assertListPropertyTranslated() {
        listVo.setList(Arrays.asList("food", "clean", "cook", "digital", "drink"));
        translator.translateFields(listVo);
        assertEquals(listVo.getList().get(0), "食品");
        assertEquals(listVo.getList().get(1), "清洁");
        assertEquals(listVo.getList().get(2), "厨具");
        assertEquals(listVo.getList().get(3), "数码");
        assertEquals(listVo.getList().get(4), "饮品");
    }

    /**
     * 测试 List 的子类型属性以及 List 为 null
     */
    @Test
    public void assertSubClassOfListPropertyTranslated() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("food");
        listVo.setArrayList(arrayList);
        translator.translateFields(listVo);
        assertEquals(listVo.getArrayList().get(0), "食品");
    }

    /**
     * 测试嵌套对象
     */
    @Test
    public void assertNestedObjectPropertyTranslated() {
        NestedPureVo nestedPureVo = new NestedPureVo(voes[0]);
        translator.translateFields(nestedPureVo);
        PureVo nestedVo = nestedPureVo.getNestedVo();
        assertEquals(nestedVo.getName(), "食品");
    }

    /**
     * 测试通过 List 嵌套对象
     */
    @Test
    public void assertListOfObjectPropertyTranslated() {
        List<PureVo> pureVos = Arrays.asList(
                new PureVo("food"),
                new PureVo("clean")
        );
        listVo.setPureVos(pureVos);
        translator.translateFields(listVo);
        assertEquals(listVo.getPureVos().get(0).getName(), "食品");
        assertEquals(listVo.getPureVos().get(1).getName(), "清洁");
    }
}
