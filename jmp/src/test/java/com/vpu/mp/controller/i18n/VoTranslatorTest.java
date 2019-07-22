package com.vpu.mp.controller.i18n;

import com.vpu.mp.controller.VoTranslator;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

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

    private VoTranslator translator;

    @Before
    public void setUp() {

        HttpServletRequest reqMock = mock(HttpServletRequest.class);
        when(reqMock.getHeader("V-Lang")).thenReturn("zh_CN");
        translator = new VoTranslator(reqMock);
        voes[0] = new CategoryVo("food");
        voes[1] = new CategoryVo("digital");
        voes[2] = new CategoryVo("clean");
        voes[3] = new CategoryVo("cook");
        voes[4] = new CategoryVo("drink");
    }

    @Test
    public void assertPropertyTranslated() {

        for (CategoryVo vo : voes) {
            translator.translateFields(vo);
        }
        assertEquals(voes[0].getName(), "食品");
        assertEquals(voes[1].getName(), "数码");
        assertEquals(voes[2].getName(), "清洁");
        assertEquals(voes[3].getName(), "厨具");
        assertEquals(voes[4].getName(), "饮品");
    }
}
