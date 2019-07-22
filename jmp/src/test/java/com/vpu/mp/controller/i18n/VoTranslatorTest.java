package com.vpu.mp.controller.i18n;

import com.vpu.mp.controller.VoTranslator;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 自动国际化翻译器测试
 *
 * @author 郑保乐
 */
public class VoTranslatorTest {

    private CategoryVo[] voes = new CategoryVo[5];

    private CategoryListVo listVo = new CategoryListVo();

    private VoTranslator translator;

    private HttpServletRequest reqMock;

    private static final String HEADER_LANG = "V-Lang";

    @Before
    public void setUp() {

        voes[0] = new CategoryVo("food");
        voes[1] = new CategoryVo("digital");
        voes[2] = new CategoryVo("clean");
        voes[3] = new CategoryVo("cook");
        voes[4] = new CategoryVo("drink");
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

        for (CategoryVo vo : voes) {
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
}
