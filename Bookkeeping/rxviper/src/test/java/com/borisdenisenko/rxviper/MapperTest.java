package com.borisdenisenko.rxviper;

import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public final class MapperTest {

  private Mapper<Integer, String> spyMapper;

  @Before
  public void setUp() {
    spyMapper = spy(new Mapper<Integer, String>() {
      @Override
      public String map(final Integer entity) {
        return String.valueOf(entity);
      }
    });
  }

  @Test
  public void shouldCallOverloadedMethod() {
    spyMapper.map(asList(1, 2, 3));

    verify(spyMapper).map(1);
    verify(spyMapper).map(2);
    verify(spyMapper).map(3);
  }

  @Test
  public void shouldCallMap() {
    spyMapper.call(42);

    verify(spyMapper).map(42);
  }
}