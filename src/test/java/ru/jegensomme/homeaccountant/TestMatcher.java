package ru.jegensomme.homeaccountant;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.jegensomme.homeaccountant.TestUtil.readListFromJsonMvcResult;

@RequiredArgsConstructor
public class TestMatcher<T> {
    private final @NonNull Class<T> clazz;
    private final @NonNull BiConsumer<T, T> assertion;
    private final @NonNull BiConsumer<Iterable<T>, Iterable<T>> iterableAssertion;

    public static <T> TestMatcher<T> usingEqualsComparator(@NonNull Class<T> clazz) {
        return new TestMatcher<>(clazz,
                (a, e) -> assertThat(a).isEqualTo(e),
                (a, e) -> assertThat(a).isEqualTo(e));
    }

    public static <T> TestMatcher<T> usingIgnoringFieldsComparator(@NonNull Class<T> clazz, String... fieldsToIgnore) {
        return new TestMatcher<>(clazz,
                (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields(fieldsToIgnore).isEqualTo(e),
                (a, e) -> assertThat(a).usingElementComparatorIgnoringFields(fieldsToIgnore).isEqualTo(e));
    }

    public void assertMatch(T actual, T expected) {
        assertion.accept(actual, expected);
    }

    @SafeVarargs
    public final void assertMatch(Iterable<T> actual, T... expected) {
        assertMatch(actual, List.of(expected));
    }

    public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        iterableAssertion.accept(actual, expected);
    }

    public ResultMatcher contentJson(T expected) {
        return result -> assertMatch(TestUtil.readFromJsonMvcResult(result, clazz), expected);
    }

    @SafeVarargs
    public final ResultMatcher contentJson(T... expected) {
        return contentJson(List.of(expected));
    }

    public ResultMatcher contentJson(Iterable<T> expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, clazz), expected);
    }
}
