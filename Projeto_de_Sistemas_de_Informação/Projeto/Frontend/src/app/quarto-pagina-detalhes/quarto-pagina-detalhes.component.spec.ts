import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuartoPaginaDetalhesComponent } from './quarto-pagina-detalhes.component';

describe('QuartoPaginaDetalhesComponent', () => {
  let component: QuartoPaginaDetalhesComponent;
  let fixture: ComponentFixture<QuartoPaginaDetalhesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuartoPaginaDetalhesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuartoPaginaDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
