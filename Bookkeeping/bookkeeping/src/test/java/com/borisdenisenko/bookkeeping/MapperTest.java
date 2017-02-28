package com.borisdenisenko.bookkeeping;

import com.borisdenisenko.bookkeeping.gateway.entity.WebContent;
import com.borisdenisenko.bookkeeping.mainscreen.domain.WebContentMapper;
import com.borisdenisenko.bookkeeping.mainscreen.domain.WebContentViewModel;
import com.borisdenisenko.rxviper.Mapper;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public final class MapperTest {

  private WebContentMapper spyMapper;

  @Before
  public void setUp() {
    spyMapper = spy(new WebContentMapper());
  }

  @Test
  public void shouldCallOverloadedMethod() {
    List<WebContent> contentList = asList(new WebContent("test.com", null, true) , new WebContent("test.com", null, true), new WebContent("test.com", null, true));
    spyMapper.map(contentList);

    verify(spyMapper).map(contentList.get(0));
    verify(spyMapper).map(contentList.get(1));
    verify(spyMapper).map(contentList.get(2));
  }
}