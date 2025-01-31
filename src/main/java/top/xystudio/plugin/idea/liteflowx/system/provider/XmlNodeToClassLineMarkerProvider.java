package top.xystudio.plugin.idea.liteflowx.system.provider;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlToken;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomUtil;
import icons.LiteFlowIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xystudio.plugin.idea.liteflowx.dom.modal.Node;
import top.xystudio.plugin.idea.liteflowx.service.JavaService;

import javax.swing.*;
import java.util.Optional;

/**
 * 实现对Node的识别以及获取Class的位置
 * @author Coder-XiaoYi
 */
public class XmlNodeToClassLineMarkerProvider extends XmlLineMarkerProvider{

    @Override
    public Optional<? extends PsiElement[]> apply(@NotNull XmlToken element) {
        DomElement domElement = DomUtil.getDomElement(element);
        if (domElement == null){
            return Optional.empty();
        }
        else if (domElement instanceof Node){
            String classValue = ((Node) domElement).getClazz().getStringValue();
            PsiClass clazz = JavaService.getInstance(element.getProject()).getClassByQualifiedName(classValue);
            if (clazz != null) {
                return Optional.of(new PsiElement[]{clazz});
            }
        }
        return Optional.empty();
    }

    @Override
    public @NotNull String getTooltip(PsiElement element, @NotNull PsiElement target) {
        String text = null;
        if (text == null && element instanceof PsiClass) {
            PsiClass psiClass = (PsiClass) element;
            text = psiClass.getQualifiedName();
        }
        if (text == null) {
            text = target.getContainingFile().getText();
        }
        return "LiteFlow Class found - " + text;
    }

    @Override
    public @Nullable Icon getIcon() {
        return LiteFlowIcons.COMPONENT_LINE_MARKER_ICON;
    }

    @Override
    public String getName() {
        return "Class statement line marker";
    }

}
